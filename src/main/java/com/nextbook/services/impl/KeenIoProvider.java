package com.nextbook.services.impl;

import com.nextbook.services.IKeenIoProvider;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 9/23/2015
 * Time: 9:07 AM
 */
@Service
public class KeenIoProvider implements IKeenIoProvider{
    @Override
    public void addKeenData(Model model) {
        model.addAttribute("keenProjectKey", projectId);
        model.addAttribute("keenReadKey", readKey);
        model.addAttribute("keenWriteKey", writeKey);
    }

    private String projectId = "560242c496773d3104dee1bd";
    private String writeKey = "4753a81c057e3986fafd79d417e0360fac38e3178562b86f689a1f3bdb26b8eab216f1691788fba257067027523a6d4a84229ca58c8e78064719e68fa9caa0efbacd9e2e3e49a4987d73a5d05ea8f38546884c1d98a79041bcd69c156eb63ae1c51c2752b88aa9665c0352df03008c22";
    private String readKey = "662087f0075540e5c66d0be895bc74a808be246ebc6ae09edd6b92bc20f8b48b358b525494e00839b1580f5612ab51aaf5fd65cfd7084997c8ceecb27241b5bcf9c5053dce2259f8bd2876819f4d43c9f09d889cdaaaa7e098580e65487d290a9a3d94f81dc0174232d86b08468e51f7";
}
