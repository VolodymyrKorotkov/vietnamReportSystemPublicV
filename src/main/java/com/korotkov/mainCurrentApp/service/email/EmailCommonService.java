package com.korotkov.mainCurrentApp.service.email;

import java.util.Map;

public interface EmailCommonService {
    public boolean sendEmail (final String templateName, final Map<String,Object> model);
}
