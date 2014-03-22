package cz.literak.sandbox.so.http.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This sample servlet parses multi-part HTTP request and saves uploaded file.
 * Date: 22. 3. 2014
 */
public class FileReceiveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        try {
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    writer.println("fieldName = " + item.getFieldName());
                    writer.println("fileName = " + item.getName());
                    writer.println("contentType = " + item.getContentType());
                    writer.println("size [bytes] = " + item.getSize());
                    File uploadedFile = File.createTempFile("temp", ".txt");
                    item.write(uploadedFile);
                    writer.println("stored as  " + uploadedFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
