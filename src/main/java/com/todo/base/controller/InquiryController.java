package com.todo.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.base.entity.InquiryEntity;
import com.todo.base.form.InquiryForm;
import com.todo.base.service.InquriyAccessService;


@Controller
public class InquiryController {

	@Autowired
	InquriyAccessService inquriyAcsServ;
	
	/**
	 * /fromにAccessした時の処理
	 * @param inauriyForm Fimport com.tutorial.base.entity.InquiryEntity;ormの初期状態を渡しておく
	 * @param model titleを渡す
	 * @return form画面
	 */
	
	@GetMapping("inquiry/form")
	public String form(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "お問い合わせ");
		
		//model.addAttribute("form", inquiryForm);
		return "inquiry/form";
	}
	
	
	/**
	 * /formに戻ったときの処理
	 * @param inquiryForm Formに入っているものを保持
	 * @param model　titleを渡す
	 * @return form画面
	 */
	@PostMapping("inquiry/form")
	public String backForm(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "お問い合わせ");
		//model.addAttribute("form", inquiryForm);
		return "inquiry/form";
	}
	/**
	 * /confirmにデータがPOSTメソッドで送信された時の処理
	 * @param inquiryForm Formの内容をチェックする
	 * @param result チェック結果にエラーがあるかの判定をする
	 * @param model titleを渡す
	 * @return
	 */
	@PostMapping("inquiry/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
							BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "お問い合わせ内容エラー");
			return "inquiry/form";
		}
		model.addAttribute("title", "お問い合わせ内容確認");
		//model.addAttribute("inquiryForm", inquiryForm); \\なくていい
			
	
		return "inquiry/confirm";
	
	}
	
	@PostMapping("inquiry/complete")
	public String complete( @Validated InquiryForm inquiryForm, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
			
		// inquiryEntityをインスタンス化、値を入れていく
		
				InquiryEntity inquiryEntity = new InquiryEntity();
				
				inquiryEntity.setInquiryType(inquiryForm.getInquiryType());
				inquiryEntity.setInquiryInfo(inquiryForm.getInquiryInfo());
				inquiryEntity.setInquiryUser(inquiryForm.getInquiryUser());
				inquiryEntity.setInquiryUserMail(inquiryForm.getInquiryUserMail());
				
				// DBへの登録処理
				inquriyAcsServ.inquiryInsert(inquiryEntity);
				
				//研修と同じにする場合は2行をコメントアウトしてね
				//redirectAttributes.addFlashAttribute("complete", "送信が完了しました");
				//redirectはgetの扱いらしい
				//return "redirect:/inquiry/form";
				
				model.addAttribute("title", "完了したよ");
				return "inquiry/complete";
				
	}


	
	

}
