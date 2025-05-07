/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyCustom;

/**
 *
 * @author ADMIN
 */
public class QueryCondition {
    private final String column;
    private final String operator;
    private final Object value;

    public QueryCondition(String column, String operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }
    
    public QueryCondition(String column) {
        this.column = column;
        this.operator=null;
        this.value = null;
    }

    // Getters and setters
    public String getColumn(){
        return column;
    }
    
    public String getOperator(){
        return operator;
    }
    
    public Object getValue(){
        return value;
    }
}

