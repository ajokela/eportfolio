<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="KolbLearning">
		<xsl:param name="entryName">
			Kolb Learning Style Inventory
		</xsl:param>

		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:text></xsl:text>
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Inventory:</fo:inline><xsl:text> </xsl:text><xsl:value-of select="entryName/value"/> 
								<fo:block></fo:block> </xsl:if -->
							<xsl:if test="dateTaken/value">
								<fo:inline font-weight="bold">Date Taken:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="dateTaken/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if
								test="(concreteExperience/value) or (reflectiveObservation/value) or (abstractConceptualization/value) or (activeExperimentation/value)">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Learning Cycle Stages Scores</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="concreteExperience/value">
								<fo:inline font-weight="bold">Concrete Experience (CE):</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="concreteExperience/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="reflectiveObservation/value">
								<fo:inline font-weight="bold">Reflective Observation (RO):</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="reflectiveObservation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="abstractConceptualization/value">
								<fo:inline font-weight="bold">Abstract Conceptualization
									(AC):</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="abstractConceptualization/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="activeExperimentation/value">
								<fo:inline font-weight="bold">Active Experimentation (AE):</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="activeExperimentation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="learningStyleType/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Learning Style Type:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="learningStyleType/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interpretation/value">
								<fo:inline font-weight="bold">Interpretation/Reaction:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:apply-templates select="interpretation/value" />
								<fo:block></fo:block>
							</xsl:if>
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
