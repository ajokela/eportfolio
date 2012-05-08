/* $Name:  $ */
/* $Id: CommunityRoleType.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.community;


/**
 * @author Matt Sheehan
 *
 */
public enum CommunityRoleType {

    MEMBER("me", "Member"),
	EVALUATOR("ev", "Evaluator"),
	ASSESSMENT_COORDINATOR("ac", "Assessment Coordinator"),
	COMMUNITY_COORDINATOR("cc", "Community Coordinator");	

	private final String code;
	private final String title;

	private CommunityRoleType(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public static CommunityRoleType getByCode(String code) {
		for (CommunityRoleType communityRoleType : values()) {
			if (communityRoleType.code.equals(code)) {
				return communityRoleType;
			}
		}
		return null;
	}
}
