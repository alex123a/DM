#!/usr/bin/env python3

from wrapping import *

TTL_FILENAME = 'simpsons.ttl'

g = model()

###############################################################################
################################################################## Helpers ####

def insert (triple):
    if triple in g: return
    g.add(triple)

def restrict (subs, pred, objs):
    for sub in subs:
        insert( (pred, RDFS.domain, sub) )
    for obj in objs:
        insert( (pred, RDFS.range, obj) )

def create_property (ns, name, property_type_names, subs=None, objs=None, parent=None):
    if not type(property_type_names)==list: property_type_names = [property_type_names]
    if subs==None: subs = []
    if objs==None: objs = []
    property_types = {
      'rdf':    RDF.Property,
      'object': OWL.ObjectProperty,
      'data':   OWL.DatatypeProperty,
    }
    
    entity = ns[name]
    for property_type_name in property_type_names:
        insert( (entity, RDF.type, property_types[property_type_name]) )
    if parent:
        insert( (entity, RDFS.subPropertyOf, parent) )
    restrict(subs, entity, objs)
    
    return entity

def set_cardinality (ns, type_entity, property_entity, cardmin=None, cardmax=None):
    if cardmin:
        cardmin_entity = ns['_%s_cardmin' % type_entity.split('#')[-1]]
        insert( (type_entity, OWL.equivalentClass, cardmin_entity) )
        insert( (cardmin_entity, RDF.type, OWL.Restriction) )
        insert( (cardmin_entity, OWL.onProperty, property_entity) )
        insert( (cardmin_entity, OWL.minCardinality, Literal(cardmin)) )
    if cardmax:
        cardmax_entity = ns['_%s_cardmax' % type_entity.split('#')[-1]]
        insert( (type_entity, OWL.equivalentClass, cardmax_entity) )
        insert( (cardmax_entity, RDF.type, OWL.Restriction) )
        insert( (cardmax_entity, OWL.onProperty, property_entity) )
        insert( (cardmax_entity, OWL.maxCardinality, Literal(cardmax)) )

def create_class (ns, name, parent=None):
    if parent==None: parent = OWL.Class
    
    entity = ns[name]
    insert( (entity, RDFS.subClassOf, parent) )
    
    return entity

###############################################################################
################################################################# Ontology ####

Person = create_class(P, 'Person')
Female = create_class(P, 'Female', Person)
Male   = create_class(P, 'Male'  , Person)

insert( (Female, RDF['label'], Literal('Female')) )
insert( (Male  , RDF['label'], Literal('Male')) )

olderThan = create_property(P, 'olderThan', 'object', [Person], [Person])
parentOf  = create_property(P, 'parentOf' , 'object', [Person], [Person])
hasName   = create_property(P, 'hasName'  , 'data'  , [Person], [XSD.string])

set_cardinality(P, Person, hasName, 1, 1)

###############################################################################
##################################################################### Data ####

marge  = N['marge']
homer  = N['homer']
maggie = N['maggie']
lisa   = N['lisa']
bart   = N['bart']

# define instances
insert( (marge , RDF['type'], P['Female']) )
insert( (homer , RDF['type'], P['Male']) )
insert( (maggie, RDF['type'], P['Female']) )
insert( (lisa  , RDF['type'], P['Female']) )
insert( (bart  , RDF['type'], P['Male']) )

# set names
insert( (marge , P['hasName'], Literal('Marge')) )
insert( (homer , P['hasName'], Literal('Homer')) )
insert( (maggie, P['hasName'], Literal('Maggie')) )
insert( (lisa  , P['hasName'], Literal('Lisa')) )
insert( (bart  , P['hasName'], Literal('Bart')) )

# parentOf relations
for parent in [marge, homer]:
    for child in [maggie, lisa, bart]:
        insert( (parent, parentOf, child) )

# older than
insert( (homer, olderThan, marge) )
insert( (marge, olderThan, bart) )
insert( (bart , olderThan, lisa) )
insert( (lisa , olderThan, maggie) )

###############################################################################
########################## store-load cycle to simulate applications split ####

g.serialize(TTL_FILENAME, 'turtle')
del g
g = Graph()
g.parse(TTL_FILENAME, format='turtle')

###############################################################################
################################################################## queries ####

q1 = \
'''
SELECT DISTINCT ?name
WHERE {
    ?mother rdf:type/rdfs:subClassOf* p:Female .
    ?maggie rdf:type/rdfs:subClassOf* p:Person .
    ?sister rdf:type/rdfs:subClassOf* p:Female .
    
    ?mother p:parentOf ?maggie .
    ?mother p:parentOf ?sister .
    
    ?maggie p:hasName "Maggie" .
    ?sister p:olderThan+ ?maggie .
    ?sister p:hasName ?name
}
'''
print('Maggie has the following older sister(s):')
pprint(query(g, q1))
print('')

q2 = \
'''
SELECT DISTINCT ?father_name ?daugther_name
WHERE {
    ?father   rdf:type/rdfs:subClassOf* p:Male .
    ?daugther rdf:type/rdfs:subClassOf* p:Female .
    
    ?father p:parentOf ?daugther .
    
    ?father   p:hasName ?father_name .
    ?daugther p:hasName ?daugther_name .
}
'''
print('All farther-daugther pairs:')
pprint(query(g, q2))
print('')

q3 = \
'''
SELECT DISTINCT ?name ?gender
WHERE {
    ?person rdf:type/rdfs:subClassOf* p:Person .
    ?person rdf:type/rdf:label ?gender .
    ?person p:hasName ?name .
}
'''
print('List all persons with gender:')
pprint(query(g, q3))
print('')

q4 = \
'''
SELECT DISTINCT ?pname ?cname
WHERE {
    ?parent rdf:type ?gender .
    ?child  rdf:type ?gender .
    ?gender rdfs:subClassOf* p:Person .
    
    ?parent p:parentOf ?child .
    
    ?parent p:hasName ?pname .
    ?child  p:hasName ?cname .
}
'''
print('List all parent-child pairs of same gender:')
pprint(query(g, q4))
print('')

q4 = \
'''
SELECT DISTINCT ?sname ?pred ?oname
WHERE {
    ?marge rdf:type/rdfs:subClassOf* p:Person .
    ?homer rdf:type/rdfs:subClassOf* p:Person .
    ?marge p:hasName "Marge" .
    ?homer p:hasName "Homer" .
    ?homer ?pred ?marge .
    
    ?sub rdf:type/rdfs:subClassOf* p:Person .
    ?obj rdf:type/rdfs:subClassOf* p:Person .
    ?sub p:hasName ?sname .
    ?obj p:hasName ?oname .
    ?sub ?pred ?obj .
}
'''
print('List all pairs of humans who share a relationship with marge and homer:')
pprint(query(g, q4))
print('')



