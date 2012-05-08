<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="ExploreInventory">
		<xsl:param name="entryName">
			Career Exploration Inventory
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:text> </xsl:text>
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name of Inventory: </strong><xsl:text> 
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="dateTaken/value">
						<strong>Date Taken:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="dateTaken/value" />
						<br />
					</xsl:if>

					<xsl:if
						test="(firstOccupation/value) or (secondOccupation/value) or (thirdOccupation/value)">
						<br />
						<strong>Occupations</strong>
						<br />
					</xsl:if>
					<xsl:if test="firstOccupation/value">
						<strong>First Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="firstOccupation/value" />
						<br />
					</xsl:if>
					<xsl:if test="secondOccupation/value">
						<strong>Second Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="secondOccupation/value" />
						<br />
					</xsl:if>
					<xsl:if test="thirdOccupation/value">
						<strong>Third Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="thirdOccupation/value" />
						<br />
					</xsl:if>

					<xsl:if
						test="(firstLeisure/value) or (secondLeisure/value) or (thirdLeisure/value)">
						<br />
						<strong>Leisure Activities</strong>
						<br />
					</xsl:if>
					<xsl:if test="firstLeisure/value">
						<strong>First Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="firstLeisure/value" />
						<br />
					</xsl:if>
					<xsl:if test="secondLeisure/value">
						<strong>Second Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="secondLeisure/value" />
						<br />
					</xsl:if>
					<xsl:if test="thirdLeisure/value">
						<strong>Third Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="thirdLeisure/value" />
						<br />
					</xsl:if>


					<xsl:if
						test="(firstLearning/value) or (secondLearning/value) or (thirdLearning/value)">
						<br />
						<strong>Learning Activities</strong>
						<br />
					</xsl:if>
					<xsl:if test="firstLearning/value">
						<strong>First Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="firstLearning/value" />
						<br />
					</xsl:if>
					<xsl:if test="secondLearning/value">
						<strong>Second Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="secondLearning/value" />
						<br />
					</xsl:if>
					<xsl:if test="thirdLearning/value">
						<strong>Third Choice:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="thirdLearning/value" />
						<br />
					</xsl:if>

					<xsl:if test="reaction/value">
						<br />
						<strong>Interpretation/Reaction:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="reaction/value" />
						<br />
					</xsl:if>
				</td>
				<td class="elemPresentationCell3">
					<xsl:choose>
						<xsl:when test="elementMaterials/material">
							<xsl:apply-templates select="elementMaterials" />
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[No Materials]</font>
						</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</table>

	</xsl:template>
</xsl:stylesheet>
