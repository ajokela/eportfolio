<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="ExploreInventory">
		<xsl:param name="entryName">
			Career Exploration Inventory
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
							<xsl:if test="dateTaken/value">
								<fo:inline font-weight="bold">Date Taken:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="dateTaken/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if
								test="(firstOccupation/value) or (secondOccupation/value) or (thirdOccupation/value)">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Occupations</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="firstOccupation/value">
								<fo:inline font-weight="bold">First Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="firstOccupation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="secondOccupation/value">
								<fo:inline font-weight="bold">Second Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="secondOccupation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="thirdOccupation/value">
								<fo:inline font-weight="bold">Third Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="thirdOccupation/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if
								test="(firstLeisure/value) or (secondLeisure/value) or (thirdLeisure/value)">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Leisure Activities</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="firstLeisure/value">
								<fo:inline font-weight="bold">First Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="firstLeisure/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="secondLeisure/value">
								<fo:inline font-weight="bold">Second Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="secondLeisure/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="thirdLeisure/value">
								<fo:inline font-weight="bold">Third Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="thirdLeisure/value" />
								<fo:block></fo:block>
							</xsl:if>


							<xsl:if
								test="(firstLearning/value) or (secondLearning/value) or (thirdLearning/value)">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Learning Activities</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="firstLearning/value">
								<fo:inline font-weight="bold">First Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="firstLearning/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="secondLearning/value">
								<fo:inline font-weight="bold">Second Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="secondLearning/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="thirdLearning/value">
								<fo:inline font-weight="bold">Third Choice:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="thirdLearning/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="reaction/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Interpretation/Reaction:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="reaction/value" />
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
