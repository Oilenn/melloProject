package com.melloProj.Mello.services;


import com.melloProj.Mello.models.system.ReferenceList;
import com.melloProj.Mello.repositories.system.ReferenceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReferenceListService {
    @Autowired
    private ReferenceListRepository referenceListRepo;

    public List<Long> getAllContentIDByRefID(Long ID) {
        Optional<ReferenceList> startCheck = referenceListRepo.findById(ID);
        if (startCheck.isEmpty()) return new ArrayList<>();

        ReferenceList start = startCheck.get();
        while (start.getPrev() != null && referenceListRepo.findById(start.getPrev()).isPresent())
            start = referenceListRepo.findById(start.getPrev()).get();

        List<Long> result = new ArrayList<>();
        while (start.getNext() != null){
            Long content = getContentIDByRefID(start.getContentID());
            if(content != null)result.add(content);
        }
        return result;
    }
    public List<ReferenceList> collectRefList(ReferenceList start){
        while (start.getPrev() != null && referenceListRepo.findById(start.getPrev()).isPresent())
            start = referenceListRepo.findById(start.getPrev()).get();

        List<ReferenceList> result = new ArrayList<>();
        while (start.getNext() != null){
            ReferenceList content = getRefListByID(start.getContentID());
            if(content != null)result.add(content);
        }
        return result;
    }
    public Long getContentIDByRefID(Long ID){
        Optional<ReferenceList> refList = referenceListRepo.findById(ID);
        return refList.map(ReferenceList::getContentID).orElse(null);
    }
    public ReferenceList getRefListByID(Long ID){
        return referenceListRepo.findById(ID).orElse(null);
    }
    public ReferenceList addToList(ReferenceList list){
        return referenceListRepo.save(list);
    }
    public ReferenceList addToList(ReferenceList prevList, ReferenceList currList){
        currList.setPrev(prevList.getId());
        prevList.setNext(currList.getId());
        referenceListRepo.save(prevList);
        return referenceListRepo.save(currList);
    }
    public ReferenceList deleteFromList(ReferenceList list){
        var id = list.getId();
        while (list.getPrev() != null && referenceListRepo.findById(list.getPrev()).isPresent())
            list = referenceListRepo.findById(list.getPrev()).get();

        if(list.getNext() == null && list.getNext() == null){
            referenceListRepo.delete(list);
            return null;
        }

        while (list.getNext() != null){
            if(list.getId() == id){
                var prev = getRefListByID(list.getPrev());
                var next = getRefListByID(list.getNext());
                prev.setNext(next.getId());
                var res = referenceListRepo.save(prev);
                next.setPrev(res.getId());
                referenceListRepo.save(next);
                referenceListRepo.delete(list);
                return res;
            }
        }
        return null;
    }
}
