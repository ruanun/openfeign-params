package com.ruanun.openfeign.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author ruan
 */
@Getter
@Setter
public class ParamConfig {
    /**
     * 参数来源获取的name
     */
    private String sourceName;
    /**
     * QUERY, HEADER
     */
    private String sourceType;

    /**
     * 如果为空则为sourceName
     */
    private String targetName;
    /**
     * QUERY, HEADER
     */
    private String targetType = "QUERY";

    /**
     * 默认拦截所有
     */
    private Set<String> includePaths = Set.of("/**");

    /**
     * 排除的path
     */
    private Set<String> excludePaths = Set.of();

    public String getTargetName() {
        return StringUtils.hasText(targetName) ? targetName : sourceName;
    }

    public boolean isValid() {
        return StringUtils.hasText(this.getSourceName()) && StringUtils.hasText(this.getSourceType()) && StringUtils.hasText(this.getTargetName());
    }
}
