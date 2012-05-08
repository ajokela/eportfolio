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
					page-width="auto" page-height="auto" margin-top="0.25in"
					margin-bottom="0.25in" margin-left="0.25in" margin-right="0.25in">
					<fo:region-body xsl:use-attribute-sets="region-body" />
					<fo:region-before display-align="before" extent="1in"
						region-name="page-header" />
					<fo:region-after display-align="after" extent="1in"
						region-name="page-footer" />
					<fo:region-start extent="0.5in" />
					<fo:region-end extent="0.5in" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="all-pages">
				<fo:static-content flow-name="page-footer"
					display-align="after">
					<fo:block xsl:use-attribute-sets="footer"><!--Page <fo:page-number/> -->
						University of Minnesota Portfolio
					</fo:block>
				</fo:static-content>
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

	<xsl:template name="ProfDevelopmentPS" match="ProfDevelopmentPS/recordsList">
		<fo:block>
			<fo:block>
				<fo:inline font-weight="bold">
					This page is an official Training Record maintained by the University
					of Minnesota
                </fo:inline>
			</fo:block>
			<fo:block>
				<fo:leader />
			</fo:block>
			<fo:table width="100%" table-layout="fixed" border-collapse="separate">
				<fo:table-header>
					<fo:table-cell font-weight="bold">
						<fo:block>Course Name</fo:block>
					</fo:table-cell>
					<fo:table-cell font-weight="bold">
						<fo:block>Status</fo:block>
					</fo:table-cell>
					<fo:table-cell font-weight="bold">
						<fo:block>Status Date</fo:block>
					</fo:table-cell>
				</fo:table-header>

				<fo:table-body>
					<xsl:for-each select="recordItem/ProfDevelopmentPSRecord">
						<fo:table-row>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="courseName/value" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="status/value" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<xsl:value-of select="statusDate/value" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:for-each>
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
	</xsl:template>


</xsl:stylesheet>