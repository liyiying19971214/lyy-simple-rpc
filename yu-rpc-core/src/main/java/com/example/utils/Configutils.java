package com.example.utils;

import cn.hutool.setting.dialect.Props;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author lyy
 * @Type cccc.java
 * @Desc
 * @date 2024/12/8 17:37
 */
public class Configutils {

      public   static <T> T loadConfig(Class<T> clazz,String prefix) throws Exception {

          String[] extensions = {".yml", ".json", ".properties"};
          //根据文件自动识别
          for (String ext : extensions) {
              StringBuffer sb = new StringBuffer("application");
              sb.append(ext);
              String filePath = sb.toString();
              if (Files.exists(Paths.get(filePath))) {
                  Props props = new Props(filePath);
                  return props.toBean(clazz, prefix);
              }
          }
          throw new Exception("找不到配置文件!");
      }
}
