package vip.softwares.appvendas.models.services;

import java.util.List;

/**
 * Created by re032629 on 19/07/2015.
 */
public class ServiceResult {
    private List<Mensagem> Messages;
    private boolean HasErrors;
    private String Result;

    public List<Mensagem> getMessages() {
        return Messages;
    }

    public void setMessages(List<Mensagem> messages) {
        Messages = messages;
    }

    public boolean isHasErrors() {
        return HasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        HasErrors = hasErrors;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
