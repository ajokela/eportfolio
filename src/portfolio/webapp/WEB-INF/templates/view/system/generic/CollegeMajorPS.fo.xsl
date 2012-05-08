<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="CollegeMajorPS">
		<xsl:param name="entryName">
			College and Major
		</xsl:param>
		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<xsl:choose>
								<xsl:when
									test="((termDesc/value/text()) != ' ')or((effdt/value/text()) != ' ')or((institutionDesc/value/text()) != ' ')or((acadCareerDesc/value/text()) != ' ')or((progActionDescr/value/text()) != ' ')or((progDescr/value/text()) != ' ')or ((planDesc/value/text()) != ' ')">
									<xsl:if test="(termDesc/value/text()) != ' '">
										<fo:inline font-weight="bold">Term:</fo:inline>
										<xsl:value-of select="termDesc/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(effdt/value/text()) != ' '">
										<xsl:value-of select="effdt/value" />
										(Date of change)
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(institutionDesc/value/text()) != ' '">
										<fo:inline font-weight="bold">Campus:</fo:inline>
										<xsl:value-of select="institutionDesc/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(acadCareerDesc/value/text()) != ' '">
										<fo:inline font-weight="bold">Career:</fo:inline>
										<xsl:value-of select="acadCareerDesc/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(progActionDescr/value/text()) != ' '">
										<fo:inline font-weight="bold">Program Action:</fo:inline>
										<xsl:value-of select="progActionDescr/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(progDescr/value/text()) != ' '">
										<fo:inline font-weight="bold">College:</fo:inline>
										<xsl:value-of select="progDescr/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="(planDesc/value/text()) != ' '">
										<fo:inline font-weight="bold">Major/Minor:</fo:inline>
										<xsl:value-of select="planDesc/value" />
										(
										<xsl:value-of select="planTypeDesc/value" />
										)
										<fo:block></fo:block>
										<fo:block></fo:block>
									</xsl:if>
								</xsl:when>
							</xsl:choose>

						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell3">
						<fo:block>
							<xsl:choose>
								<xsl:when test="elementMaterials/material">
									<xsl:apply-templates select="elementMaterials" />
								</xsl:when>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>


	</xsl:template>
</xsl:stylesheet>
