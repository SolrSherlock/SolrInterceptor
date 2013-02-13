# SolrInterceptor #
Status: *pre-alpha*<br/>
Latest edit: 20130213
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

## ToDo ##
Mavenize the project<br/>
Create a full unit test suite

## License ##
Apache 2
