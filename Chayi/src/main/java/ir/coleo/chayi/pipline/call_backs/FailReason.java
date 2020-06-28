package ir.coleo.chayi.pipline.call_backs;

/**
 * دلایل ناتمام ماندن یک ریکوست
 */
public enum FailReason {

    Network,
    Token,
    Authentication,
    Request,
    Server,
    Unknown,
    NullResponse,
    Function_Name_Costume_Request

}
