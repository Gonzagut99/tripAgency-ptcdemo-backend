package com.tripagency.ptc.ptcagencydemo.general.utils.exceptions;

import org.springframework.http.HttpStatus;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.GeneralException;

public class HtpExceptionUtils {
    public static GeneralException processHttpException(Exception e) {
        if (e instanceof GeneralException generalException) {
            return generalException;
        }
        return new GeneralException("Ocurrió un error desconocido.", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}
