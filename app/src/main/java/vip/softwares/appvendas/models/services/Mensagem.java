package vip.softwares.appvendas.models.services;

/**
 * Created by re032629 on 19/07/2015.
 */
public class Mensagem {
    private boolean isError;
    private String Codigo;
    private String Origem;
    private String OperationResult;
    private String ValueResult;

    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getOrigem() {
        return Origem;
    }

    public void setOrigem(String origem) {
        Origem = origem;
    }

    public String getOperationResult() {
        return OperationResult;
    }

    public void setOperationResult(String operationResult) {
        OperationResult = operationResult;
    }

    public String getValueResult() {
        return ValueResult;
    }

    public void setValueResult(String valueResult) {
        ValueResult = valueResult;
    }
}
