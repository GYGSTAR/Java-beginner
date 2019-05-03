import java.io.FileNotFoundException;
import java.io.FileReader;
 
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.Test;

//import java.util.List;

/**
 * 	�򵥵ķ������ܴ�����ܵ����⣬������ѯ�������������������˶�̬���ص�iframeҳ���У����������������������β�ͬ�ļ��ܷ�������ʱû��ѧ����ô���룬�˽⵽�˷ֲ�ʽ���洦��ʽ
 *	�������ҵ���һ������ֱ�ӻ�ȡ���۵�api�����˸��ݾ�
 * 	http://music.163.com/api/v1/resource/comments/R_SO_4_id //(?limit=a&&offset=b)//���Խ�һ����չ��ҳ
 */
public class Crawler {
    @Test
    public void test() {
    	
    	//ʹ�ô���IP��ַ:�е㲻�ȶ�����ֻҪ���ʴ������࣬ûë����
    	HttpHost proxy = new HttpHost("111.177.182.42",9999);
    	
    	//ѡ��Ŀ����ַ�����ó�ʱʱ�䣺https://music.163.com/#/song?id=1311319058
        HttpGet httpGet = new HttpGet("https://music.163.com/api/v1/resource/comments/R_SO_4_1311319058");
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());

    	//ģ�������
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64)  AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");

        //��ʼ����Ӧ
        String responseStr = "";
        
        //����ʵ��
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        
        //�쳣�������
        try {
        	//����
            httpClient = HttpClientBuilder.create().build();
            HttpClientContext context = HttpClientContext.create();
            
            //��Ӧ��ȡ
            response = httpClient.execute(httpGet, context);
            
            //��Ӧ״̬
            int state = response.getStatusLine().getStatusCode();
            if (state != 200)
            {
            	responseStr = "";
            	System.out.println("��200");
            }
            
            //ִ��GET����
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                responseStr = EntityUtils.toString(entity, "utf-8");
                System.out.println("��ȡ�������£�");
            }
            //�ַ���ע�⣡
            //���Դ�����Ҫ��ȡ����������ע�ⲻҪԽ����
            new Crawler().ReadJsonStr(responseStr, 14);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
                if (httpClient != null)
                    httpClient.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (responseStr == null)
        {
            System.out.println("��ȡ����Ϊ��");
            return;
        }

/*====================================JSOUP����==========================================*/
//�ⲿ��������Jsoup�����õ��ַ�����Ȼ��ֱ�Ӵ�json�ַ���û�����ϣ�����ֱ�Ӵ���û�м��ܵ���վ
//        Document document = Jsoup.parse(responseStr);
//        
//        List<Element> elements = document.getAllElements();;
//        
//        elements.forEach(element -> {
//        	//ƥ��ڵ���Ϣ
//            for (Element e : element.select("div")) {
//            	System.out.print(e.text() + ": ");
//            }
//        });
//    }
/*=============================��JSON�ַ����Ĵ���=======================================*/
    }
    
    public void ReadJsonStr(String str, int len)
    {
    	//��ȡ����json����
    	str = str.replace("null", "\"\"");
    	
    	JsonParser parser = new JsonParser();
    	JsonObject jsonObj = parser.parse(str).getAsJsonObject();
    	
    	//һ��һ���ȡJson����
    	JsonArray hotCommentsArr = jsonObj.get("hotComments").getAsJsonArray();
    	for(int i = 0; i < len; i++) {
    		JsonObject hotComments = hotCommentsArr.get(i).getAsJsonObject();
    		JsonObject hotUserIp = hotComments.get("user").getAsJsonObject();
    		String UserIp = hotUserIp.get("nickname").getAsString();
    		String hotComment = hotComments.get("content").getAsString();
    		
    		//����û���
    		System.out.print("  " + UserIp + ":\n");
    		//�����������
    		System.out.println(hotComment  + "\n");
    	}
/*=================================================��ȡ��JSON��ʽ============================================*/
//    	{
//    		"user": {
//    			"locationInfo": null,
//    			"userId": 44393510,
//    			"remarkName": null,
//    			"expertTags": null,
//    			"nickname": "�����˰�",
//    			"userType": 0,
//    			"vipRights": {
//    				"associator": {
//    					"vipCode": 100,
//    					"rights": true
//    				},
//    				"musicPackage": null,
//    				"redVipAnnualCount": -1
//    			},
//    			"authStatus": 0,
//    			"avatarUrl": "http://p1.music.126.net/7ystH7U9B--070HBE7zDTQ==/109951163399384130.jpg",
//    			"vipType": 11,
//    			"experts": null
//    		},
//    		"beReplied": [],
//    		"pendantData": {
//    			"id": 5002,
//    			"imageUrl": "http://p1.music.126.net/tFjGw0GRZ-Oyf6r5vH4kFQ==/109951163313123168.jpg"
//    		},
//    		"showFloorComment": null,
//    		"status": 0,
//    		"commentLocationType": 0,
//    		"parentCommentId": 0,
//    		"decoration": null,
//    		"repliedMark": false,
//    		"likedCount": 158945,
//    		"liked": false,
//    		"commentId": 17351419,
//    		"expressionUrl": null,
//    		"time": 1430864801561,
//    		"content": "�ԴӸ����Ǽҹ�������vlv�����ں�ִ����Ҳû�������ʢʱ�ڣ�����������Ĵ�С�����������������������ˣ���ҹ��ʱ�䣬�����������¥�����߳������׸衰I used to rule the world ��ǧ�������������ס�"
//    	}
/*======================================================================================================*/
    }
}