package cz.literak.sandbox.so.http.upload;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

/**
 * This sample servlet will create a file in temp directory and then upload it as multi-part HTTP request
 * to final destination passed as parameter.
 * Date: 21. 3. 2014
 */
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");
        assertNotEmpty("content", content);
        File file = File.createTempFile("temp", ".txt");
        Files.write(file.toPath(), content.getBytes());
        String url = request.getParameter("url");
        assertNotEmpty("url", url);

        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            FileBody bin = new FileBody(file);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(reqEntity);

            CloseableHttpResponse postResponse = httpClient.execute(httpPost);
            try {
                writer.println(postResponse.getStatusLine());
                HttpEntity postResponseEntity = postResponse.getEntity();
                if (postResponseEntity != null) {
                    writer.println(EntityUtils.toString(postResponseEntity));
                }
            } finally {
                postResponse.close();
            }
        } finally {
            httpClient.close();
        }
    }

    private void assertNotEmpty(String param, String value) throws ServletException {
        if (value == null || value.trim().length() == 0) {
            throw new ServletException("Mandatory parameter '" + param + "' is empty!");
        }
    }
}
