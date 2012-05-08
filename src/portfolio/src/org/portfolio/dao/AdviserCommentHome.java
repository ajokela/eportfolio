/* $Name:  $ */
/* $Id: AdviserCommentHome.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.AdviserComment;

public interface AdviserCommentHome {

    List<AdviserComment> findByAdvisee(String adviseeId);

    void insert(AdviserComment adviserComment);

    void update(AdviserComment adviserComment);

    void delete(int id);

    AdviserComment findById(int id);

}
