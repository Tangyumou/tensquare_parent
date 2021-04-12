package entity;

public class StatusCode {
    public static final Integer OK = 20000;// 成功
    public static final Integer ERROR = 20001;// 失败
    public static final Integer LOGINERROR = 20002;// 用户名或密码错误
    public static final Integer ACCESSERROR = 20003;// 权限不足
    public static final Integer REMOTEERROR = 20004;// 远程调用失败
    public static final Integer REPERROR = 20005;// 重复操作
}
