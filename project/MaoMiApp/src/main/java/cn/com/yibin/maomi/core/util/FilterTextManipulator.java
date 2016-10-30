package cn.com.yibin.maomi.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
public class FilterTextManipulator
{
   private String startToken = "/~";

   private String endToken = "~/";
   /**
    * @see net.mlw.vlh.adapter.util.TextManipulator#manipulate(String, Object)
    */
   public StringBuffer manipulate(StringBuffer text, Map<String,String> model)
   {
      if (model == null)
      {
         model = new HashMap<String,String>();
      }

      boolean inverse = false;

      for (int start = 0, end = text.indexOf(endToken); ((end = text.indexOf(endToken)) >= 0);)
      {
         start = text.lastIndexOf(startToken, end);
         String key = text.substring(start + 2, text.indexOf(":", start));

         //If this is an or statement
         if (key.indexOf(',') != -1)
         {
            for (StringTokenizer st = new StringTokenizer(key, ",");st.hasMoreElements();)
            {
               Object modelValue = model.get((key = st.nextToken()));
               if (modelValue instanceof String && (((String) modelValue).length() == 0))
               {
                  continue;
               }
               else if (modelValue != null)
               {
                  break;
               }
            }
         }
         else if (key.indexOf('|') != -1)
         {
            for (StringTokenizer st = new StringTokenizer(key, "|");st.hasMoreElements();)
            {
               Object modelValue = model.get((key = st.nextToken()));
               if (modelValue instanceof String && (((String) modelValue).length() == 0))
               {
                  continue;
               }
               else if (modelValue != null)
               {
                  break;
               }
            }
         }
         else if (key.indexOf('&') != -1)
         {
            for (StringTokenizer st = new StringTokenizer(key, "&");st.hasMoreElements();)
            {
               Object modelValue = model.get((key = st.nextToken()));
               if (modelValue == null || modelValue instanceof String && (((String) modelValue).length() == 0))
               {
                  break;
               }
            }
         }

         if ((inverse = key.startsWith("!")))
         {
            key = key.substring(1, key.length());
         }

         Object modelValue = model.get(key);
         //If its an empty string replace it with a null.
         if (modelValue instanceof String && (((String) modelValue).length() == 0))
         {
            modelValue = null;
         }

         if (inverse)
         {
            if ((modelValue == null))
            {
               text.replace(start, end + 2, text.substring(text.indexOf(":", start) + 1, end));
            }
            else
            {
               text.replace(start, end + 2, "");
            }
         }
         else
         {
            if ((modelValue != null))
            {
               text.replace(start, end + 2, text.substring(text.indexOf(":", start) + 1, end));
            }
            else
            {
               text.replace(start, end + 2, "");
            }
         }

      }

      return text;
   }

   /**
    * @param endToken The endToken to set.
    */
   public void setEndToken(String endToken)
   {
      this.endToken = endToken;
   }

   /**
    * @param startToken The startToken to set.
    */
   public void setStartToken(String startToken)
   {
      this.startToken = startToken;
   }
}
