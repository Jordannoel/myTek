package com.epsi.guez.mytek.service.impl;

import com.epsi.guez.mytek.dao.GroupeDao;
import com.epsi.guez.mytek.exception.FormInvalideException;
import com.epsi.guez.mytek.model.Groupe;
import com.epsi.guez.mytek.service.GroupeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeServiceImpl implements GroupeService {

    private GroupeDao groupeDao;

    public GroupeServiceImpl(GroupeDao groupeDao) {
        this.groupeDao = groupeDao;
    }

    @Override
    public List<Groupe> findAll() {
        return groupeDao.findAll();
    }

    public Groupe findOneById(Long id){
        return groupeDao.findOneById(id);
    }

    public Groupe findOneByNomGroupe(String nomGroupe){
        return groupeDao.findOneByNomGroupe(nomGroupe);
    }

    public boolean nomGroupeDejaExistant(String nomGroupe) {
        return groupeDao.countByNomGroupe(nomGroupe) > 0;
    }

}
