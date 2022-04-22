package com.example.utils.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author zfl
 * @create 2022/1/27 18:00
 * @description
 */
public class MyBatisCommentGeneratorUtils implements CommentGenerator {
    private boolean suppressDate;
    private boolean suppressAllComments;
    private final String currentDateStr;

    public MyBatisCommentGeneratorUtils() {
        super();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    /**
     * 添加属性配置
     * @param properties 属性
     */
    @Override
    public void addConfigurationProperties(Properties properties) {

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    private boolean isTrue(String str){
        return StringUtils.isNotBlank(str) && Boolean.parseBoolean(str);
    }

    /**
     * 添加字段的描述
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
        System.out.println("field comment:" + field.getJavaDocLines());
    }

    /**
     * Adds the field comment.
     *
     * @param field             the field
     * @param introspectedTable
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        topLevelClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getRemarks());
        sb.append(" ");
        sb.append(introspectedTable.getTableType());
        sb.append(" ");
        sb.append(getDateString());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
        System.out.println("model class comment:" + topLevelClass.getJavaDocLines());
    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        addClassComment(innerClass,introspectedTable,false);
    }

    /**
     * Adds the inner class comment.
     *
     * @param innerClass        the inner class
     * @param introspectedTable the introspected table
     * @param markAsDoNotDelete
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        sb.setLength(0);
        sb.append(" * @author zfl");
        sb.append(" ");
        sb.append(currentDateStr);

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */");
        System.out.println("inner class comment:" + innerClass.getJavaDocLines());
    }

    /**
     * Adds the enum comment.
     *
     * @param innerEnum         the inner enum
     * @param introspectedTable
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**");
        addJavadocTag(innerEnum, false);
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        innerEnum.addJavaDocLine(" */");

        System.out.println("add enum：" + innerEnum.getJavaDocLines());
    }

    /**
     * Adds the getter comment.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");

        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" ");
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */");
        System.out.println("method get set comment:" + method.getJavaDocLines());
    }

    /**
     * Adds the setter comment.
     *
     * @param method             the method
     * @param introspectedTable  the introspected table
     * @param introspectedColumn
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */");
    }

    /**
     * Adds the general method comment.
     *
     * @param method            the method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(" @author zfl");
        sb.append(" * ");
        String s = method.getName();
        sb.append(' ');
        sb.append(s);
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }
    @Override
    public void addComment(XmlElement xmlElement) {

    }
    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }
}

