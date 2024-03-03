package com.ruanun.openfeign.params.strategys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ruan
 */
public class StrategyContainer {
    private final Map<String, IParamGettingStrategy> paramGettingMap;

    private final Map<String, IParamSettingStrategy> paramSettingMap;

    public StrategyContainer(List<IParamGettingStrategy> paramGettingList, List<IParamSettingStrategy> paramSettingList) {
        this.paramGettingMap = new HashMap<>();
        if (Objects.nonNull(paramGettingList)) {
            paramGettingList.forEach(e -> paramGettingMap.put(e.type(), e));
        }
        this.paramSettingMap = new HashMap<>();
        if (Objects.nonNull(paramSettingList)) {
            paramSettingList.forEach(e -> paramSettingMap.put(e.type(), e));
        }
    }

    public IParamGettingStrategy getParamGetting(String type) {
        IParamGettingStrategy paramGetting = paramGettingMap.get(type);
        if (!Objects.isNull(paramGetting)) {
            return paramGetting;
        }
        paramGetting = paramGettingMap.get(type.toLowerCase());
        if (!Objects.isNull(paramGetting)) {
            return paramGetting;
        }
        return paramGettingMap.get(type.toUpperCase());
    }

    public IParamSettingStrategy getParamSetting(String type) {
        IParamSettingStrategy paramSetting = paramSettingMap.get(type);
        if (!Objects.isNull(paramSetting)) {
            return paramSetting;
        }
        paramSetting = paramSettingMap.get(type.toLowerCase());
        if (!Objects.isNull(paramSetting)) {
            return paramSetting;
        }
        return paramSettingMap.get(type.toUpperCase());
    }
}
