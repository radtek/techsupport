package com.aisino2.techsupport.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aisino2.core.dao.Page;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.dao.IAttachmentDao;
import com.aisino2.techsupport.domain.Attachment;
import com.aisino2.techsupport.service.IAttachmentService;

/**
 * 附件服务 
 *
 */
@Component
public class AttachmentServiceImpl implements IAttachmentService {

	private IAttachmentDao attachment_dao;
	
	public void insertAttachment(Attachment attachment) {
		attachment_dao.insert(attachment);
	}

	public void updateAttachment(Attachment attachment) {
		attachment_dao.update(attachment);
	}

	public void removeAttachment(Attachment attachment) {
		attachment_dao.delete(attachment);
	}

	public void removeAttachment(Attachment attachment, String upload_dir){
		attachment = attachment_dao.get(attachment);
		attachment_dao.delete(attachment);
		
		File upload_file = new File(upload_dir + "/" + attachment.getAttachmentPath());
		if(upload_file.exists()){
			upload_file.delete();
		}
	}
	public List<Attachment> queryAttachment(Attachment attachment) {
		if(attachment==null)
			attachment=new Attachment();
		return attachment_dao.query(attachment);
	}

	public Page queryAttachmentForPage(Map<String, Object> map, int pageno,
			int pagesize, String sort, String desc) {
		return attachment_dao.queryForPage(map, pageno, pagesize, sort, desc);
	}

	public Attachment getAttachment(Attachment attachment) {
		if(attachment == null || attachment.getAttachmentId() == null )
			throw new RuntimeException("附件的ID为空");
		
		return attachment_dao.get(attachment);
	}

	@Resource(name="attachmentDaoImpl")
	public void setAttachment_dao(IAttachmentDao attachment_dao) {
		this.attachment_dao = attachment_dao;
	}

	@Override
	public void removeInvailAttachment() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("junkAttachment", 1);
		Page page = queryAttachmentForPage(map,1,99999,null,"desc");
		String upload_dir = Constants.APPLICATION_SERVERCONTEXT_REALPATH + Constants.APPLICATION_UPLOAD_DIR;
		for(Attachment at : (List<Attachment>)page.getData()){
			removeAttachment(at, upload_dir);
		}
	}

}
