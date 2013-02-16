# SolrInterceptor #
Status: *pre-alpha*<br/>
Latest edit: 20130215
## Background ##
SolrInterceptor exists inside Solr's update request process chain, in solrconfig.xml,  as illustrated:

	<updateRequestProcessorChain name="harvest" default="true">
      <processor class="solr.RunUpdateProcessorFactory"/>
      <processor class="org.apache.solr.update.TopicQuestsDocumentProcessFactory">
        <str name="inputField">hello</str>
      </processor>
      <processor class="solr.LogUpdateProcessorFactory"/>
    </updateRequestProcessorChain>

The request processor chain handles documents processed by Solr. The first line in that XML calls Solr to process the document. The second line is the interceptor. The interceptor converts the Solr document to a JSON string and sends it out over a TCP socket to the SolrAgentCoordinator. The last entry in that XML deals with logging.

## Installation ##

The projects classes, in the /org directory, go into Solr's webapps/WEB-INF/classes directory.
The following jar files are required in Solr's webapps/WEB-INF/lib directory:
- commons-beanutils<latestversion>.jar
- commons-collections<latestversion>.jar
- ezmorph<latestversion>.jar
- json-lib-2.4-jdk15.jar

Additionally, solrconfig.xml must include the modified processor chain as described above. That file is found in /collections/config/

The file agents.properties must be in the Solr root directory from which Solr is booted. If Solr throws a FileNotFound exception, it is likely because it cannot find this file.

## ToDo ##
Mavenize the project<br/>
Create a full unit test suite

## License ##
Apache 2
