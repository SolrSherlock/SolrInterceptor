# SolrInterceptor #
Status: *pre-alpha*<br/>
Latest edit: 20131011
## Background ##
SolrInterceptor exists inside Solr's update request process chain, in solrconfig.xml,  as illustrated (read solrconfig.xml for details):

	<updateRequestProcessorChain name="harvest" default="true">
      <processor class="solr.RunUpdateProcessorFactory"/>
      <processor class="org.apache.solr.update.TopicQuestsDocumentProcessFactory">
        <str name="inputField">hello</str>
      </processor>
      <processor class="solr.LogUpdateProcessorFactory"/>
    </updateRequestProcessorChain>

The request processor chain handles documents processed by Solr. The first line in that XML calls Solr to process the document. The second line is the interceptor. The interceptor converts the Solr document to a JSON string and sends it out over a TCP socket to the SolrAgentCoordinator. The last entry in that XML deals with logging.

## Installation ##

Build SolrUpdateResponseHandler.jar with build.xml.
The following jar files are required in Solr's webapps/WEB-INF/lib directory:
- json_simple-1.2.jar
- SolrUpdateResponseHandler.jar

Additionally, solrconfig.xml must include the modified processor chain as described above. That file is installed in /collections/config/

The file agents.properties must be in the Solr root directory from which Solr is booted. If Solr throws a FileNotFound exception, it is likely because it cannot find this file.

## Important Note ##
agents.properties includes a port assignment.

**That port assignment might need to be *changed* depending on the installation.**

**Please note that the port is used by all SolrAgentCoordinator installations.**

## CHANGE HISTORY ##
20131011
	With latest Solr versions, required to take the SolrDocument apart rather than make a JSONObject directly from it. The issue was bad JSON syntax in messages sent to the agents.
20130619
	No code changes; just upgraded Solr jars
20130414
	Swapped in much simpler JSON library. Now running from jar file rather than class files
20130218
	Dropped creating a new DocumentProcessor object since we do not care what changed; we are using it for external, not internal purposes.  Changed DocumentProcessor to threaded. Results in improved performance, and no blocking of Solr if the listener drops off.

## ToDo ##
Mavenize Project

Create a full unit test suite

## License ##
Apache 2
