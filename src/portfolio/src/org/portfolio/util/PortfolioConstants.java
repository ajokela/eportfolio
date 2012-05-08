/* $Name:  $ */
/* $Id: PortfolioConstants.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 *
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Copyrights:
 *
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 *
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Portions Copyright (c) 2003 The University of Delaware.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
*/

package org.portfolio.util;

/**
 * Constants to be shared among all classes
 *
 * @author <a href="felipeen@udel.edu">Luis F.C. Mendes</a> - University of Delaware
 * @author Jack Brown - University of Minnesota
 * @version $Revision 1.0$
 */
public interface PortfolioConstants {
    public static final String PERSON_SESSION_KEY = "Person";
    public static final String PERSON_ID_SESSION_KEY = "PersonId";
    public static final String VIEW_MANAGER_SESSION_KEY = "views";
    public static final String ADVISEE_LIST_SESSION_KEY = "adviseeList";
    public static final String VIEWTREE_SESSION_KEY = "ViewTree";
    public static final String NODE_SESSION_KEY = "Node";
    public static final String ACTION_REQUEST_PARAM = "action";
    public static final String EXTERNAL_PRINCIPAL_SESSION_KEY = "external.principal";
    public static final String AUTHENTICATOR_SESSION_KEY = "authenticator";
    public static final String ELEMENT_FORM_SESSION_KEY = "dataDef";
    public static final String ELEMENT_NAME_SESSION_KEY = "element.name";
    public static final String ELEMENT_COMMENTS_SESSION_KEY = "elementComments";
    public static final String COMMENTS_SESSION_KEY = "comments";
    public static final String COMMENT_PARENT = "commentParent";
    public static final String ELEMENT_DATA_XML = "elementDataXML";
    public static final String XSL_TEMPLATE_NAME = "xslTemplateName";
    public static final String ELEMENT_TAGS_SESSION_KEY = "elementTags";

    public static final String ENTER_MODE = "enter";
    public static final String VIEW_MODE = "view";
    public static final String SHARE_MODE = "share";
    public static final String MANAGE_MODE = "manage";
    // added for bug 392
    /**
     * Key for the FILE_ERROR session attribute
     */
    public static final String FILE_ERROR_SESSION_KEY = "file.error.exists";


    /**
     * constant for the user type of MEMBER
     */
    public static final String MEMBER = "MEMBER";

    /**
     * constant for the user type of GUEST
     */
    public static final String GUEST = "GUEST";

    /**
     * No one may enter!
     */
    public static final int NO_PERMISSIONS = Integer.MAX_VALUE;
    /**
     * Flag for checking to see if permissions are greater than
     */
    public static final int MAX_PERMISSIONS = 1;
    /**
     * Flag for checking to see if permissions exceed
     */
    public static final int MIN_PERMISSIONS = 0;

    // the following are used for form field validation
    public static final int TWENTY_FIVE_WORDS = 128;
    public static final String TWENTY_FIVE_WORDS_DESC = "25 words (128 characters)";
    public static final int FIFTY_WORDS = 256;
    public static final String FIFTY_WORDS_DESC = "50 words (256 characters)";
    public static final int ONE_HUNDRED_WORDS = 512;
    public static final String ONE_HUNDRED_WORDS_DESC = "100 words (512 characters)";
    public static final int TWO_FIFTY_WORDS = 1280;
    public static final String TWO_FIFTY_WORDS_DESC = "250 words (1280 characters)";
    public static final int EIGHT_HUNDRED_WORDS = 4000;
    public static final String EIGHT_HUNDRED_WORDS_DESC = "800 words (4000 characters)";
    // added for textfield validation
    public static final int TWO_CHARS = 2;
    public static final String TWO_CHARS_DESC = "2 characters";
    public static final int FOUR_CHARS = 4;
    public static final String FOUR_CHARS_DESC = "4 characters";
    public static final int FIVE_CHARS = 5;
    public static final String FIVE_CHARS_DESC = "5 characters";
    public static final int SIX_CHARS = 6;
    public static final String SIX_CHARS_DESC = "6 characters";
    public static final int TEN_CHARS = 10;
    public static final String TEN_CHARS_DESC = "10 characters";
    public static final int FIFTEEN_CHARS = 15;
    public static final String FIFTEEN_CHARS_DESC = "15 characters";
    public static final int TWENTY_FIVE_CHARS = 25;
    public static final String TWENTY_FIVE_CHARS_DESC = "25 characters";
    public static final int THIRTY_CHARS = 30;
    public static final String THIRTY_CHARS_DESC = "30 characters";
    public static final int THIRTY_FIVE_CHARS = 35;
    public static final String THIRTY_FIVE_CHARS_DESC = "35 characters";
    public static final int FIFTY_CHARS = 50;
    public static final String FIFTY_CHARS_DESC = "50 characters";
    public static final int SIXTY_CHARS = 60;
    public static final String SIXTY_CHARS_DESC = "60 characters";
    // maximum length of filename
    public static final int MAXFILENAMELENGTH = 256;

    // File storage location flags.
    // These should be accessable to the outside world...
    /**
     * flag indicating storage to temp directory on upload
     */
    public static final int TEMP = 1;
    /**
     * flag indicating storing to "permanent" location upon upload
     */
    public static final int REGULAR = 2;

    public static final int PERMANENT_REPOSITORY = REGULAR;

    public static final int TEMP_REPOSITORY = TEMP;


    // Default image rendering values
    public static final int IMAGE_WIDTH = 200;
    public static final int IMAGE_HEIGHT = 200;

    // constants for file size computations
    /**
     * constant value for a gigabyte
     */
    public static final long GIGABYTE = 1073741824L;
    /**
     * constant value for a megabyte
     */
    public static final int MEGABYTE = 1048576;
    /**
     * constant value for a kilobyte
     */
    public static final int KILOBYTE = 1024;

    public static final String FO_VIEW_TYPE = "foViewType";

    public static final String TEMPLATE_ENCODING = "ISO-8859-1";

    public static final String GRAD_PLAN_ELEMENT_NAME = "Graduation Plan";

    public static final String QUICKSHARE_TEMPLATE_ID = "2";

    public static final String CUSTOM_TEMPLATE_ID = "1";

    public static final String PORT_TREE = "portTree";

    public static final String ATTACH_TO_ELEMENT_FOLDER_LIST = "attachToElementFolderList";

    public static final String CACHED_EXT_DATA = "cachedExtData";

    public static final String MATERIAL_FILTER_TAG_LIST = "materialFilterTagList";

    /** Constants for tree used in material attachTo elements functionality */
    public static final String PORT_ATTACH_TO_TREE = "portAttachToTree";
    public static final String ATTACH_TO_MATERIAL_ENTRY_IDS = "attachToMaterialEntryIds";
    public static final String ATTACH_TO_MATERIAL_NAMES = "attachToMaterialNames";
    public static final String ATTACH_TO_TREE_FILTER_TYPE = "attachToTreeFilterType";
    public static final String ATTACH_TO_TREE_FILTER_BY = "attachToTreeFilterBy";
    public static final String ATTACH_TO_TREE_FILTER_BY_TEXT = "attachToTreeFilterByText";

    public static final String UPLOADED_MATERIAL_CLASSNAME = "org.portfolio.model.UploadedMaterial";

    public static final int TREE_TOP_NODE_ID_LENGTH = 4;

    public static final String PORTICO_FOLDER = "Portico Uploaded";

    public static final String TAG_LIST = "tagList";
	public static final String ADMIN = "ADMIN";


}
