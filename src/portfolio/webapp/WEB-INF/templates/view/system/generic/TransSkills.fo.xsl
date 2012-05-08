<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="TransSkills">
		<xsl:param name="entryName">
			Transferable Skills
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
									test="((surveyDate/value/text()) != ' ') or ((selfKnowledgeTotal/value/text()) != ' ') or ((selfKnowledgeAve/value/text()) != ' ') or ((effectiveCommTotal/value/text()) != ' ') or ((effectiveCommAve/value/text()) != ' ') or ((processControlTotal/value/text()) != ' ') or ((processControlAve/value/text()) != ' ') or ((visioningTotal/value/text()) != ' ') or ((visioningAve/value/text()) != ' ') or ((interpretation/value/text()) != ' ')">
									<xsl:if test="surveyDate/value">
										<fo:inline font-weight="bold">Date Taken:</fo:inline>
										<xsl:value-of select="surveyDate/value" />
										<fo:block></fo:block>
									</xsl:if>
									<fo:block></fo:block>
									<fo:inline font-weight="bold">Scores:</fo:inline>
									<fo:block></fo:block>
									<xsl:if
										test="((selfKnowledgeTotal/value/text()) != ' ') or ((selfKnowledgeAve/value/text()) != ' ')">
										<fo:inline font-weight="bold">Self Knowledge Score:</fo:inline>
										<xsl:if test="selfKnowledgeTotal/value">
											<xsl:value-of select="selfKnowledgeTotal/value" />
											total;
										</xsl:if>
										<xsl:if test="selfKnowledgeAve/value">
											<xsl:value-of select="selfKnowledgeAve/value" />
											average
										</xsl:if>
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if
										test="((effectiveCommTotal/value/text()) != ' ') or ((effectiveCommAve/value/text()) != ' ')">
										<fo:inline font-weight="bold">Effective Communication
											Score:</fo:inline>
										<xsl:if test="effectiveCommTotal/value">
											<xsl:value-of select="effectiveCommTotal/value" />
											total;
										</xsl:if>
										<xsl:if test="effectiveCommAve/value">
											<xsl:value-of select="effectiveCommAve/value" />
											average
										</xsl:if>
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if
										test="((processControlTotal/value/text()) != ' ') or ((processControlAve/value/text()) != ' ')">
										<fo:inline font-weight="bold">Process Control Score:</fo:inline>
										<xsl:if test="processControlTotal/value">
											<xsl:value-of select="processControlTotal/value" />
											total;
										</xsl:if>
										<xsl:if test="processControlAve/value">
											<xsl:value-of select="processControlAve/value" />
											average
										</xsl:if>
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if
										test="((visioningTotal/value/text()) != ' ') or ((visioningAve/value/text()) != ' ')">
										<fo:inline font-weight="bold">Visioning Score:</fo:inline>
										<xsl:if test="visioningTotal/value">
											<xsl:value-of select="visioningTotal/value" />
											total;
										</xsl:if>
										<xsl:if test="visioningAve/value">
											<xsl:value-of select="visioningAve/value" />
											average
										</xsl:if>
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="interpretation/value">
										<fo:inline font-weight="bold">Interpretation:</fo:inline>
										<fo:block></fo:block>
										<xsl:apply-templates select="interpretation/value" />
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
