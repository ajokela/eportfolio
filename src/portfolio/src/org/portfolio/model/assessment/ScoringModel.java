/* $Name:  $ */
/* $Id: ScoringModel.java,v 1.12 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.assessment;

import org.portfolio.util.ArrayUtil;

/**
 * This class represents a scoring model.
 * 
 * The purpose of this class is to encapsulate scoring models used for
 * assessment score entry.
 */
public class ScoringModel implements Comparable<ScoringModel> {

    private Integer id;
    private String name;
    private String description;
    private String dataType;
    private String valueType;
    private final String[] scoreList;
    private final int[] quantifiedList;

    public ScoringModel(
            Integer identifier,
            String name,
            String description,
            String dataType,
            String valueType,
            String valueSet,
            String quantifiedSet) {
        this.id = identifier;
        this.name = name;
        this.description = description;
        this.dataType = dataType;
        this.valueType = valueType;

        if (this.valueType.equals("discrete values")) {
            this.scoreList = valueSet.split(",");
            this.quantifiedList = ArrayUtil.stringsToInts(quantifiedSet.split(","));
        } else if (valueType.equals("percentage")) {
            this.scoreList = createPercentageValueArray();
            this.quantifiedList = ArrayUtil.stringsToInts(createPercentageValueArray());
        } else {
            throw new IllegalArgumentException("unrecognize valueType: " + valueType);
        }
    }

    private String[] createPercentageValueArray() {
        String[] values = new String[101];
        for (int i = 0, j = 100; i < values.length; i++, j--) {
            values[i] = "" + j;
        }
        return values;
    }

    /**
     * Returns a <code>List</code> of <code>scores</code>s for this scoring
     * model.
     */
    public String[] getScores() {
        return this.scoreList;
    }

    /**
     * Returns a <code>List</code> of <code>quantified values</code>s for
     * this scoring model.
     */
    public int[] getQuantifiedScores() {
        return this.quantifiedList;
    }

    /**
     * Returns a unique identifier for this scoring model.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Returns the name of this scoring model.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description for this scoring model.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the data type of this scoring model.
     */
    public String getDataType() {
        return this.dataType;
    }

    /**
     * Returns the value type for this scoring model.
     */
    public String getValueType() {
        return this.valueType;
    }

    /**
     * Its visible in Assessment model Builder if value type is discrete values
     * and the scores.size is less than 5
     * 
     * @return
     */
    public boolean isVisibleInAM(String amFormat) {
        if ("rubric".equals(amFormat))
            return ("discrete values".equals(this.valueType) && getScores().length <= 5);
        else
            return true;
    }

    /**
     * Returns a human-readable interperetation of this scoring model.
     */
    public String toString() {
        return this.getName();
    }

    /**
     * Returns the comparison between to scoring model objects.
     */
    public int compareTo(ScoringModel object) {
        int result = getName().compareTo(object.getName());
        if (result == 0) {
            result = getDescription().compareTo(object.getDescription());
        }
        return result;
    }
}
