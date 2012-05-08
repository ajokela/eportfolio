/* $Name:  $ */
/* $Id: ObjectiveManagerImpl.java,v 1.9 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.assessment;

import java.util.List;

import org.portfolio.dao.assessment.ObjectiveHome;
import org.portfolio.model.assessment.Objective;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Matt Sheehan
 * 
 */
@Service("objectiveManager")
public class ObjectiveManagerImpl implements ObjectiveManager {
    
    private static final LogService logService = new LogService(ObjectiveManagerImpl.class);

    @Autowired
    private ObjectiveHome objectiveHome;

    public Objective getObjectiveById(int objectiveId) {
        return objectiveHome.findById(objectiveId);
    }

    @Transactional
    public void deleteObjective(Objective objective) {
        Integer parentId = objective.getParentId();

        objectiveHome.delete(objective.getId());

        if (parentId != null) {
            Objective parent = objectiveHome.findById(parentId);
            List<Objective> subObjectives = parent.getSubObjectives();
            int order = 1;
            for (Objective subObjective : subObjectives) {
                subObjective.setOrder(order);
                objectiveHome.update(subObjective);
                order++;
            }
        }
    }

    @Transactional
    public void saveObjective(Objective objective) {
        if (objective.getId() == null) {
            int numSiblings;
            if (objective.getParentId() == null) {
                numSiblings = objectiveHome.findNumberOfTopLevelObjectives();
            } else {
                numSiblings = objectiveHome.findNumberOfChildrenByObjectiveId(objective.getParentId());
            }
            objective.setOrder(numSiblings + 1);
            objectiveHome.insert(objective);
        } else {
            objectiveHome.update(objective);
        }
    }

    @Transactional
    public void moveObjectiveDown(Objective objective) {
        Integer order = objective.getOrder();
        Objective otherObective = objectiveHome.findByParentIdAndOrder(objective.getParentId(), order + 1);
        if (otherObective != null) {
            switchOrder(objective, otherObective);
        }
    }

    @Transactional
    public void moveObjectiveUp(Objective objective) {
        Integer order = objective.getOrder();
        if (order > 1) {
            Objective otherObective = objectiveHome.findByParentIdAndOrder(objective.getParentId(), order - 1);
            switchOrder(objective, otherObective);
        }
    }

    private void switchOrder(Objective o1, Objective o2) {
        Integer o1Order = o1.getOrder();
        Integer o2Order = o2.getOrder();
        o1.setOrder(o2Order);
        o2.setOrder(o1Order);
        objectiveHome.update(o1);
        objectiveHome.update(o2);
    }

    @Transactional
    public void saveObjectives(List<Objective> subObjectives) {
        for (Objective objective : subObjectives) {
            saveObjective(objective);
        }
    }

    @Transactional
    public Objective copyObjectiveSet(Objective origObjectiveSet, Integer destinationCommunityId) {
        return copyObjective(origObjectiveSet, destinationCommunityId, null);
    }

    private Objective copyObjective(Objective origObjective, Integer destinationCommunityId, Objective newParent) {
        Objective newObj = new Objective();
        newObj.setCommunityId(destinationCommunityId);
        newObj.setDescription(origObjective.getDescription());
        //newObj.setName(origObjective.getName() + (newParent == null ? " (copy)" : ""));
        newObj.setName(origObjective.getName()); // bk
        logService.debug("newObj.setName: " + newObj.getName());
        newObj.setOrder(origObjective.getOrder());
        newObj.setParentId(newParent == null ? null : newParent.getId());

        objectiveHome.insert(newObj);

        for (Objective subobjective : origObjective.getSubObjectives()) {
            copyObjective(subobjective, destinationCommunityId, newObj);
        }
        return newObj;
    }
}
