<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:attribute-set name="region-body">
		<xsl:attribute name="column-gap">12pt</xsl:attribute>
		<xsl:attribute name="column-count">1</xsl:attribute>
		<xsl:attribute name="margin-left">.5in</xsl:attribute>
		<xsl:attribute name="margin-bottom">.5in</xsl:attribute>
		<xsl:attribute name="margin-right">.5in</xsl:attribute>
		<xsl:attribute name="margin-top">.5in</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="body">
		<xsl:attribute name="font-family">Helvetica, Arial, sans-serif</xsl:attribute>
		<xsl:attribute name="font-size">80%</xsl:attribute>
		<!-- This property is changed from its value in css file -->
		<xsl:attribute name="color">#333</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="footer">
		<xsl:attribute name="font-family">Helvetica, Arial, sans-serif</xsl:attribute>
		<xsl:attribute name="font-size">50%</xsl:attribute>
		<!-- This property is changed from its value in css file -->
		<xsl:attribute name="color">#000066</xsl:attribute>
		<xsl:attribute name="text-align">right</xsl:attribute>
	</xsl:attribute-set>

	<xsl:param name="termName"></xsl:param>
	<xsl:template match="ospiTemplate">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="all-pages"
					page-width="auto" page-height="auto">
					<fo:region-body xsl:use-attribute-sets="region-body" />
					<fo:region-before display-align="before" extent="1in"
						region-name="page-header" />
					<fo:region-after display-align="after" extent="1in"
						region-name="page-footer" />
					<fo:region-start extent="1in" />
					<fo:region-end extent="1in" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="all-pages">
				<fo:flow flow-name="xsl-region-body" xsl:use-attribute-sets="body">
					<fo:block></fo:block>
					<fo:block></fo:block>
					<xsl:for-each select="category">
						<xsl:for-each select="subcategory">
							<xsl:apply-templates />
						</xsl:for-each>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template name="StudentTerm" match="StudentTerm">
		<xsl:if test="entryName/value = $termName">
			<fo:block>
				<fo:block font-weight="bold">
					U of M record of enrollment, credits, grades and GPA
					<fo:block></fo:block>
					<fo:inline font-weight="bold">Term:</fo:inline>
					<xsl:value-of select="entryName/value" />
				</fo:block>
				<fo:block>
					<fo:leader />
				</fo:block>
				<fo:table width="100%" table-layout="fixed"
					border-collapse="separate">

					<fo:table-header>
						<fo:table-cell font-weight="bold">
							<fo:block>Course</fo:block>
						</fo:table-cell>
						<fo:table-cell font-weight="bold">
							<fo:block>Course title</fo:block>
						</fo:table-cell>
						<fo:table-cell font-weight="bold">
							<fo:block>Credits taken</fo:block>
						</fo:table-cell>
						<fo:table-cell font-weight="bold">
							<fo:block>Grade</fo:block>
						</fo:table-cell>
						<fo:table-cell font-weight="bold">
							<fo:block>Grading basis</fo:block>
						</fo:table-cell>
						<fo:table-cell font-weight="bold">
							<fo:block>Points</fo:block>
						</fo:table-cell>
					</fo:table-header>

					<fo:table-body>
						<xsl:for-each select="recordsList/recordItem">
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="StudentTermRecord/subject/value" />
										<xsl:value-of select="StudentTermRecord/catalogNbr/value" />
										-
										<xsl:value-of select="StudentTermRecord/classSection/value" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="StudentTermRecord/entryName/value" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="StudentTermRecord/untTaken/value" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="StudentTermRecord/crseGradeOff/value" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="StudentTermRecord/gradingBasisEnrl/value" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block>
										<xsl:value-of select="StudentTermRecord/courseGradePoints/value" />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>


						<fo:table-row>
							<fo:table-cell number-columns-spanned="6">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>


						<fo:table-row>
							<fo:table-cell>
								<fo:block>Term totals:</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									GPA:
									<xsl:value-of select="curGpa/value" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="totalCredits/value" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block></fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block></fo:block>
							</fo:table-cell>

							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="gradePoints/value" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>


						<fo:table-row>
							<fo:table-cell>
								<fo:block>Cumulative totals:</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									GPA:
									<xsl:value-of select="cumGpa/value" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="cumTotalTaken/value" />
								</fo:block>
							</fo:table-cell>

							<fo:table-cell number-columns-spanned="2">
								<fo:block>
									Credits passed:
									<xsl:value-of select="cumTotalPassed/value" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="totGradePoints/value" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>

					</fo:table-body>
				</fo:table>
				<fo:block>
					<fo:leader />
				</fo:block>
				<fo:block font-weight="bold">
					Information provided by University of Minnesota
					Administrative System
                </fo:block>

			</fo:block>
		</xsl:if>
	</xsl:template>


</xsl:stylesheet>