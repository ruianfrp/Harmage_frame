package com.harmonycloud.service.imp;

import com.harmonycloud.dao.DictionaryDao;
import com.harmonycloud.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionarySeviceImp implements DictionaryService {

    @Autowired
    DictionaryDao dictionaryDao;
}