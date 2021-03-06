package action.goodAction;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import model.TbShangpin;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.goods.IInsertOrUpdateService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/*
 *   #auther:李琪
 *   #date: 2019/12/21
 *   #description
 */
@Controller
public class addGoodAction extends ActionSupport implements ModelDriven<TbShangpin> {


    private IInsertOrUpdateService service;
    @Autowired
    public void setService(IInsertOrUpdateService service) {
        this.service = service;
    }

    private TbShangpin tbShangPin;
    @Autowired
    public void setTbShangPin(TbShangpin tbShangPin) {
        this.tbShangPin = tbShangPin;
    }

    private File file;
    //文件类型
    private String fileContentType;
    //文件名称
    private  String fileFileName;

    @Override
    public String execute() throws Exception {
        String path = ServletActionContext.getServletContext().getRealPath("/images");
        //上传文件类型和上传文件地址
        System.out.println(path);

        System.out.println(fileFileName);
        System.out.println(file.getName());


        fileFileName= UUID.randomUUID().toString().replaceAll("-","")+
                fileFileName.substring(fileFileName.indexOf("."))
        ;
        tbShangPin.setTupian(fileFileName);

        File dest = new File(path,fileFileName);
        try {
            FileUtils.copyFile(file,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //下面是新增自动生成的商品资料，如日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());
        tbShangPin.setAddtime(time);
        service.insertOrUpdate(tbShangPin);
        return SUCCESS;





    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    @Override
    public TbShangpin getModel() {
        return tbShangPin;
    }
}
