# cloginstance-service

ClogInstance Micro Service can be used to create aliases in Elastic Search against an Index. 

Primary reason of the existence of this service is to have multiple alias against single index in elastic search. Currently problem is with every cloud instance of portal a new index in elastic search is getting created, which is increasing the volume of shards. To overcome this problem, going forward there will just one index and each portal instance will create an alias against that index. This will bring the infrastructure management under control. 

# Installation:

ClogInstance Micro Service needs a MySQL service in Cloud Foundry. MySQL service needs to be provisioned before pushing this service in cloud foundry. Schema is available at the root folder - "clogInstanceSchema.sql". Following details needs to be configured in manifest.yml (sample YML available - manifest.yml.sample)

* DB host
* DB port
* DB Schema
* DB user name 
* DB user name password
* Index name on which alias will be created
* Elastic Search base URL
* Group Service base URL

Note: This service works on "validate" mode and hence will not create the schema during deployment. 