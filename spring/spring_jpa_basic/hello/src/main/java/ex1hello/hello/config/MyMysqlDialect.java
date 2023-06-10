package ex1hello.hello.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyMysqlDialect extends MySQL5Dialect {
    public MyMysqlDialect() {
        registerFunction("group_concat2", new StandardSQLFunction("group_concat2", StandardBasicTypes.STRING));
    }
}
