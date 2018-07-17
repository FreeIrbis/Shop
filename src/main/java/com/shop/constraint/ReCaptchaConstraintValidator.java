package com.shop.constraint;

import com.shop.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReCaptchaConstraintValidator implements ConstraintValidator<ValidReCaptcha, String> {

   private static Logger log = LoggerFactory.getLogger(ReCaptchaConstraintValidator.class);

   @Autowired
   private ReCaptchaService reCaptchaService;

   @Override
   public void initialize(ValidReCaptcha constraintAnnotation) {

   }

   @Override
   public boolean isValid(String reCaptchaResponse, ConstraintValidatorContext context) {

      if (reCaptchaResponse == null || reCaptchaResponse.isEmpty()){
         log.info("reCaptchaResponse == null || reCaptchaResponse.isEmpty()");
         return true;
      }

      return reCaptchaService.validate(reCaptchaResponse);
   }

}
