package com.bupt.pcncad.controller.search;

import com.bupt.pcncad.service.search.ICompanySearchService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: naruto
 * Date: 13-3-3
 * Time: 下午8:44
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

//    @Autowired
//    @Qualifier("CompanyIndexProcess")
//    private AbstractIndexProcess companyIndexProcess;

    @Autowired
    private ICompanySearchService companySearchService;

//    @RequestMapping("/search")
//    public String search(@RequestParam("key") String keyWord, ModelMap modelMap) throws Exception {
//        //keyWord = new String(keyWord.getBytes("ISO-8859-1"), "UTF-8");
//        Hits companyHits = companyIndexProcess.searchHits("name", keyWord);
//        Set<JSONObject> companySet = new HashSet<JSONObject>();
//        for (int i = 0; i < companyHits.length(); i++) {
//            Map map = new HashMap();
//            map.put("name",companyHits.doc(i).getField("name").stringValue());
//            JSONObject jsonObject = JSONObject.fromObject(map);
//            companySet.add(jsonObject);
//        }
//
//
//        try{
//            if(companySet.size() == 0) {
//                Map map = new HashMap();
//                map.put("name","无");
//                modelMap.put("companies",JSONObject.fromObject(map));
//            }
//            else
//                modelMap.put("companies", companySet);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "search/search";
//    }

    @RequestMapping("/find")                                  //1 careerTalk 2 recruit 0 company
    public void find(ModelMap modelMap,@RequestParam("key")String keyword,
                     @RequestParam(value = "type",required = false)Integer type) throws Exception{
        type = (type == null) ? 0 : type;
        List list = null;
        if(type == 0)
            list = this.companySearchService.search(keyword,0);
        else
            list = this.companySearchService.findBestSearch(keyword);
        if(list.size() == 0) {
            Map map = new HashMap();
            map.put("name","无");
            JSONObject jsonObject = JSONObject.fromObject(map);
            List list1 = new ArrayList();
            list1.add(jsonObject);
            modelMap.put("companies",list1);
        }
        else
            modelMap.put("companies",list);
    }

    @RequestMapping("/rebuild")
    public void rebuildIndex() throws Exception{
        this.companySearchService.rebuildIndex();
    }
}
