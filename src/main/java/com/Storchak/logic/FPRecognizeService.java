package com.Storchak.logic;

import com.Storchak.Exceptions.UserNotFoundException;
import com.Storchak.dao.FPDao;
import com.Storchak.dao.UsersDao;
import com.Storchak.dto.FPTemplate;
import com.Storchak.dto.User;
import com.Storchak.utils.FPFilter;
import com.Storchak.utils.FPTemplateMaker;
import com.Storchak.utils.FPTemplateMatcher;

import java.io.IOException;
import java.util.List;

public class FPRecognizeService {
    private FPDao fpDao;
    private UsersDao usersDao;

    public FPRecognizeService(FPDao fpDao, UsersDao usersDao) {
        this.fpDao = fpDao;
        this.usersDao = usersDao;
    }

    public User identifyFp(int[] imageData) throws IOException, UserNotFoundException {
        int[] filteredImageData = FPFilter.filterImage(imageData);
        FPTemplate template = FPTemplateMaker.createTemplate(filteredImageData, null);
        List<Long> usersIdList = usersDao.getUsersId();
        for (Long x : usersIdList) {
            List<FPTemplate> fpTemplates = fpDao.getTemplatesOfUser(x);
            for (FPTemplate dataBaseTemplate : fpTemplates) {
                if (FPTemplateMatcher.compare(dataBaseTemplate, template)) {
                    return dataBaseTemplate.getUser();
                }
            }
        }
        throw new UserNotFoundException("User not found");
    }


    public void appendFp(User user, int[] imageData) throws IOException {
        int[] filteredImageData = FPFilter.filterImage(imageData);
        FPTemplate template = FPTemplateMaker.createTemplate(filteredImageData, user);
        fpDao.createFPTemplateEnd(template.getFpTemplateEndPointList());
        fpDao.createFPTemplateBranching(template.getFpTemplateBranchingPointList());
        fpDao.createFPTemplate(template);
    }
}
