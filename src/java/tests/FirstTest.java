/**
   Copyright [2013] [Jack Park]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package tests;
import java.util.*;

import net.sf.json.JSONObject;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;

/**
 * @author park
 * <p>Test to see if we can turn a SolrInputDocument into a JSON string</p>
 */
public class FirstTest {

	/**
	 * 
	 */
	public FirstTest() {
		System.out.println("Hello");
		try {
			runTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
{"locator":{"boost":1,"firstValue":"123ABC","name":"locator","value":"123ABC","valueCount":1,"values":["123ABC"]},
 "list":{"boost":1,"firstValue":"Hello","name":"list","value":["Hello","GoodBye"],"valueCount":2,"values":["Hello","GoodBye"]}}
	 * @throws Exception
	 */
	void runTest() throws Exception {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("locator", "123ABC");
		List<String>x = new ArrayList<String>();
		x.add("Hello");
		x.add("GoodBye");
		doc.addField("list",x);
		JSONObject j = JSONObject.fromObject(_toMap(doc));
		System.out.println(j.toString());
	}
	
	Map<String,Object>toMap(SolrInputDocument doc) {
		Iterator<String>itr = doc.keySet().iterator();
		Map<String,Object> result = new HashMap<String,Object>();
		String key;
		Object o;
		Map<String,Object> temp;
		while (itr.hasNext()) {
			key = itr.next();
			o =  doc.getFieldValue(key);//doc.get(key); //doc.getField(key);
			System.out.println(o);
		//	temp = (Map<String,Object>)o;
		//	o = temp.get("value");
		//	result.put(key, o);
		}
		return result;
	}
	
	Map<String,Object> _toMap(SolrInputDocument doc) {
		Map<String,Object> result = new HashMap<String,Object>();
		Iterator<SolrInputField> itr = doc.iterator();
		SolrInputField f;
		Object v;
		String key;
		while (itr.hasNext()) {
			f = itr.next();
//			System.out.println(f);
			v = f.getValue();
//			System.out.println("  "+v);
			key = f.getName();
			result.put(key, v);
		}
		return result;
	}

}
