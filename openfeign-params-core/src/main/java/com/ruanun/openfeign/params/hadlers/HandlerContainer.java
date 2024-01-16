package com.ruanun.openfeign.params.hadlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ruan
 */
public class HandlerContainer {
    private final Map<String, IParamGettingHandler> paramGettingHandlerMap;

    private final Map<String, IParamSettingHandler> paramSettingHandlerMap;

    public HandlerContainer(List<IParamGettingHandler> paramGettingHandlers, List<IParamSettingHandler> paramSettingHandlers) {
        this.paramGettingHandlerMap = new HashMap<>();
        if (Objects.nonNull(paramGettingHandlers)) {
            paramGettingHandlers.forEach(paramSettingHandler -> paramGettingHandlerMap.put(paramSettingHandler.type(), paramSettingHandler));
        }
        this.paramSettingHandlerMap = new HashMap<>();
        if (Objects.nonNull(paramSettingHandlers)) {
            paramSettingHandlers.forEach(paramSettingHandler -> paramSettingHandlerMap.put(paramSettingHandler.type(), paramSettingHandler));
        }
    }

    public IParamGettingHandler getParamGettingHandler(String type) {
        IParamGettingHandler paramGettingHandler = paramGettingHandlerMap.get(type);
        if (!Objects.isNull(paramGettingHandler)) {
            return paramGettingHandler;
        }
        paramGettingHandler = paramGettingHandlerMap.get(type.toLowerCase());
        if (!Objects.isNull(paramGettingHandler)) {
            return paramGettingHandler;
        }
        return paramGettingHandlerMap.get(type.toUpperCase());
    }

    public IParamSettingHandler getParamSettingHandler(String type) {
        IParamSettingHandler paramSettingHandler = paramSettingHandlerMap.get(type);
        if (!Objects.isNull(paramSettingHandler)) {
            return paramSettingHandler;
        }
        paramSettingHandler = paramSettingHandlerMap.get(type.toLowerCase());
        if (!Objects.isNull(paramSettingHandler)) {
            return paramSettingHandler;
        }
        return paramSettingHandlerMap.get(type.toUpperCase());
    }
}
