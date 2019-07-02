package ${daoPackage};

import ${doPackage}.${className}DO;

public interface ${className}DAO {


    ${className}DO selectByPrimaryKey(Long <#list list as l><#if l_index == 0>${l.propName}</#if></#list>);

    boolean deleteByPrimaryKey(Long <#list list as l><#if l_index == 0>${l.propName}</#if></#list>);

    int insert(${className}DO ${doNameLower}DO);

    int insertSelective(${className}DO ${doNameLower}DO);

    void updateByPrimaryKeySelective(${className}DO  ${doNameLower}DO);
        
    void updateByPrimaryKey(${className}DO  ${doNameLower}DO);
}


