/* $Name:  $ */
/* $Id: AssessmentModelBuilderAction.java,v 1.20 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.assessment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.PerformanceDescriptor;
import org.portfolio.model.assessment.ScoringModel;
import org.portfolio.util.RequiredInjection;

/**
 *
 * @author Vijay Rajagopal
 * Nov 21, 2008
 */
public class AssessmentModelBuilderAction extends BaseAction {

    private AssessmentModelManager assessmentModelManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String step = request.getParameter("step");
        String format = request.getParameter("format");
        
        String communityId = request.getParameter("communityId");
        
        if (step == null) {
            String amId = request.getParameter("assessmentModelId");
            if (amId != null) {
                try {
                    AssessmentModelForm modelForm = new AssessmentModelForm();
                    modelForm.setCommunityId(new Integer(communityId));
                    modelForm.setIdentifier(new Integer(amId));

                    prepareForEditAssessmentModel(modelForm);
                    session.setAttribute("assessmentModelForm", modelForm);
                } catch (NumberFormatException ne) {
                	logService.debug(ne);
                }
            } else {
                session.setAttribute("assessmentModelForm", new AssessmentModelForm());
            }
            step = "step1";
        }

        AssessmentModelForm modelForm = (AssessmentModelForm) session.getAttribute("assessmentModelForm");
        if (modelForm != null && modelForm.getCommunityId() == null) {
            modelForm.setCommunityId(Integer.parseInt(communityId));
            modelForm.setFormat(format);
        }
        if ("step2".equals(step)) {
            request.setAttribute("scoringModels", assessmentModelManager.getRubricScoringModels(modelForm.getFormat()));
        } else if ("step3".equals(step)) {
            prepareStep3(request, modelForm);
        } else if ("step4".equals(step)) {
            String action = request.getParameter("stepAction");
            if ("move".equals(action)) {
                String moveObjId = request.getParameter("moveObj");
                String moveType = request.getParameter("moveType");
                moveObjective(request, modelForm, moveObjId, moveType);
            } else if ("backFromStep5".equals(action)) {
                preservePerformanceDescriptors(request, modelForm);
            } else {
                prepareStep4(request, modelForm);
            }
        } else if ("step5".equals(step)) {
            prepareStep5(request, modelForm);
        } else if ("finish".equals(step)) {
            // TODO (constant)
            if (modelForm.getReleaseStatus().equals("DRAFT")) {
                modelForm.setReleaseStatus("RELEASED"); // TODO (constant)

            }
            finishModel(request,modelForm);
            saveAssessmentModel(request, modelForm);
            session.removeAttribute("assessmentModelForm");
        } else if ("SaveForLater".equals(step)) {
            finishModel(request,modelForm);
            saveAssessmentModel(request, modelForm);
            step = "finish";
        }

        if ("finish".equals(step)) {
            ActionForward fwd = mapping.findForward(step);
            StringBuffer path = new StringBuffer(fwd.getPath());
            path.append("?communityId=").append(modelForm.getCommunityId());
            return new ActionForward(path.toString(), true);
        } else {
            request.setAttribute("format", format);
            request.setAttribute("communityId", communityId);
            return mapping.findForward(step);
        }
    }

    public void fillCommunityObjectives(AssessmentModelForm modelForm) {

        List<AssessedObjective> objectives = null;
        if ((objectives = modelForm.getCommunityObjectives()) == null) {
            objectives = assessmentModelManager.getObjectivesListForCommunity(modelForm.getCommunityId().toString());
            modelForm.setCommunityObjectives(objectives);
            modelForm.setParentCommunityObjectives(null);
        }
    }

    /**
     * Finishes the model based on the format of the rubric before saving the model
     * If Basic - get to step 3 before u save it
     * @param request
     * @param modelForm
     */
    public void finishModel(HttpServletRequest request, AssessmentModelForm modelForm) {
            // If Basic; Finish Step 2 by preparing for Step 3 before saving
            if("basic".equals(modelForm.getFormat())){
                prepareStep3(request, modelForm);
            }
    }

    /*
     * Fills the Community Objectives
     */
    private void prepareStep3(HttpServletRequest request, AssessmentModelForm modelForm) {
        // Set the Scoring Values based on the Scoring Type
        if (modelForm.getScoringId() != null) {
            ScoringModel sm = assessmentModelManager.getScoringModel(modelForm.getScoringId());
            modelForm.setScoringModel(sm);
        }
        fillCommunityObjectives(modelForm);
    }

    private void prepareStep4(HttpServletRequest request, AssessmentModelForm modelForm) {

        // Set selected Objectives appropriately - Ideally objective.selected should be set in the jsp 
        // page using the appropriate bean
        String[] params = request.getParameterValues("selected");
        if (params != null) {
            List<String> paramList = new ArrayList<String>(Arrays.asList(params));
            List<AssessedObjective> objectives = modelForm.getCommunityObjectives();
            for (AssessedObjective obj : objectives) {
                if (paramList.contains(obj.getId().toString())) {
                    obj.setSelected(true);
                } else {
                    obj.setSelected(false);
                }
            }
        }
    // ~ Set selected objectives
    }

    private void moveObjective(HttpServletRequest request, AssessmentModelForm modelForm, String moveObjId, String moveType) {
        List<AssessedObjective> objectives = modelForm.getAssessedObjectives();
        int index = 0;
        for (AssessedObjective obj : objectives) {
            if (moveObjId.equals(obj.getId().toString())) {
                break;
            }
            index++;
        }
        int newIndex = 0;
        if ("up".equals(moveType)) {
            newIndex = index - 1;
        } else if ("down".equals(moveType)) {
            newIndex = index + 1;
        }
        if (newIndex != -1 && newIndex != objectives.size()) {
            // Swap Objectives at index and newIndex
            AssessedObjective srcObj = objectives.get(index);
            AssessedObjective destObj = objectives.get(newIndex);
            srcObj.setDisplaySequence(new Integer(newIndex));
            destObj.setDisplaySequence(new Integer(index));
            objectives.set(index, destObj);
            objectives.set(newIndex, srcObj);
        }
    }

    private void prepareForEditAssessmentModel(AssessmentModelForm modelForm) {
        AssessmentModel model = assessmentModelManager.getAssessmentModelById(modelForm.getIdentifier());
        
        // Duplicate Assessment Model
        // If Release status is retired: 
        //    1. Create a new assessment Model with ParentAMId = AMId parameter passed
        if("RETIRED".equals(model.getReleaseStatus())) // TODO: Constant
        {
            model.setReleaseStatus("DRAFT"); // Sets the release status of new AM = DRAFT
            model.setParentAssessmentModelId(model.getId());
            
            // Ensures that new assessment model is created
            model.setId(null); 
            modelForm.setIdentifier(null);
            
        }
        
        // 1. Fill Community Objectives
        fillCommunityObjectives(modelForm);

        // Fill the basic properties
        modelForm.setName(model.getName());
        modelForm.setDescription(model.getDescription());
        modelForm.setScoringId(model.getScoringModel().getId().toString());
        modelForm.setParentAssessmentModelId(model.getParentAssesmentModelId());
        
        // 2. Fill the assesmet Objectives based on the order
        List<AssessedObjective> objectives = model.getAssessedObjectives();
        modelForm.setAssessedObjectives(objectives);

        // 2. Fill the selected field in community objectives

        HashMap<String, AssessedObjective> map = new HashMap<String, AssessedObjective>();
        for (AssessedObjective obj : objectives) {
            map.put(obj.getId().toString(), obj);
        }

        List<AssessedObjective> comObjectives = modelForm.getCommunityObjectives();
        for (AssessedObjective obj : comObjectives) {
            if (map.containsKey(obj.getId().toString())) {
                obj.setSelected(true);
            }
        }
    }

    private void prepareStep5(HttpServletRequest request, AssessmentModelForm modelForm) {
        List<AssessedObjective> objectives = modelForm.getAssessedObjectives();
        int cols = modelForm.getValues().length;
        for (AssessedObjective obj : objectives) {
            List<PerformanceDescriptor> perfDesc = obj.getPerformanceDescriptors();
            int curSize = perfDesc.size();
            if (curSize < cols) {
                for (; curSize < cols; curSize++) {
                    perfDesc.add(new PerformanceDescriptor());
                }
            } else if (curSize > cols) {
                for (; curSize > cols; curSize--) {
                    perfDesc.remove(curSize - 1);
                }
            }
        }
    }

    /**
     * Save the assessment model
     * @param request
     * @param modelForm
     */
    public void saveAssessmentModel(HttpServletRequest request, AssessmentModelForm modelForm) {

        if (modelForm.getFormat().equals("rubric")) {
            preservePerformanceDescriptors(request, modelForm);
        }
        // Save the assessment model...
        Integer assessmentModelId = assessmentModelManager.saveAssessmentModel(getAssessmentModel(modelForm));

        modelForm.setIdentifier(assessmentModelId);
    }

    /**
     * Gets the Performance Descriptors from the request and stores them in assessment objectives
     * @param request
     * @param modelForm
     */
    public void preservePerformanceDescriptors(HttpServletRequest request, AssessmentModelForm modelForm) {
        // Set the Performance Descriptors
        // TODO: Ideally would like it to be automatically populated using struts tag
        /*
    	String[] params = request.getParameterValues("performanceDescriptors");
        String[] extParams = request.getParameterValues("extPerformanceDescriptors");
        */
    	
    	List<String> prams = new ArrayList<String>();
    	List<String> xPrams= new ArrayList<String>();
    	
    	for(int i=1; i<=512; ++i) {
    		for(int j=1; j<=512; ++j) {
    			String p = request.getParameter("performanceDescriptors_" + i + "_" + j);
    			String xp= request.getParameter("extPerformanceDescriptors_" + i + "_" + j);
    			
    			if(p != null && xp != null) {
    				prams.add(p);
    				xPrams.add(xp);
    			}
    		}
    	}
    	
    	String[] params = new String[prams.size()];
    	String[] extParams = new String[xPrams.size()];
    	
    	params = prams.toArray(params);
    	extParams = xPrams.toArray(extParams);
    	
        int requestIndex = 0;
        String[] scoreValues = modelForm.getValues();
        List<AssessedObjective> objectives = modelForm.getAssessedObjectives();
        for (AssessedObjective obj : objectives) {
            List<PerformanceDescriptor> perfDescriptors = obj.getPerformanceDescriptors();
            int scoreIndex = 0;
            for (PerformanceDescriptor perf : perfDescriptors) {
                // The below check is needed to make sure that the params are less compared to perfDescriptors.size
                // This case occurs when 5 step rubric is made initially and then changed to 4 step rubric 
                //    by going back.
                if (requestIndex < params.length) {
                    perf.setName(params[requestIndex]);
                    perf.setDescription(extParams[requestIndex]);
                    // The Below check is needed when 5 level rubric is changed to 4 level rubric
                    if (scoreIndex < scoreValues.length) {
                        perf.setScoreValue(scoreValues[scoreIndex++]);
                    }
                    requestIndex++;
                }
            }
        }
    // End Set the Performance Descriptors
    }

    /**
     * Gets the assessment Model object from AssessmentModelForm
     * @param modelForm
     * @return
     */
    public AssessmentModel getAssessmentModel(AssessmentModelForm modelForm) {
        AssessmentModel model = new AssessmentModel();
        model.setId(modelForm.getIdentifier());
        model.setParentAssessmentModelId(modelForm.getParentAssessmentModelId());
        model.setName(modelForm.getName());
        model.setDescription(modelForm.getDescription());
        model.setScoringModel(modelForm.getScoringModel());
        model.setAssessedObjectives(modelForm.getAssessedObjectives());
        model.setCommunityId(modelForm.getCommunityId());
        model.setReleaseStatus(modelForm.getReleaseStatus());
        model.setFormat(modelForm.getFormat());

        return model;
    }

    @RequiredInjection
    public void setAssessmentModelManager(AssessmentModelManager assessmentModelManager) {
        this.assessmentModelManager = assessmentModelManager;
    }
}
