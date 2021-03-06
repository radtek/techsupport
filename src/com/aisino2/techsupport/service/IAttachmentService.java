package com.aisino2.techsupport.service;

import java.util.List;
import java.util.Map;

import com.aisino2.core.dao.Page;
import com.aisino2.techsupport.domain.Attachment;


/**
 *
 * 附件服务接口
 */
public interface IAttachmentService {
	/**
	 * 添加附件
	 * @param attachment 附件信息
	
	 */
	void insertAttachment(Attachment attachment);
	void updateAttachment(Attachment attachment);
	void removeAttachment(Attachment attachment);
	void removeAttachment(Attachment attachment, 
			String upload_dir);
	List<Attachment> queryAttachment(Attachment attachment);
	Page queryAttachmentForPage(Map<String,Object> map,int pageno,int pagesize,String sort,String desc);
	
	Attachment getAttachment(Attachment attachment);
	
	void removeInvailAttachment();
}
