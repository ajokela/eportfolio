<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="AssessScores">
		<xsl:param name="entryName">
			Assessment Scores
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Exam Name:</strong><xsl:text> 
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="dateTaken/value">
						<strong>Date Taken:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="dateTaken/value" />
						<br />
					</xsl:if>

					<xsl:if test="(section1/value) or (score1/value) or (percent1/value)">
						<br />
					</xsl:if>
					<xsl:if test="section1/value">
						<strong>
							<xsl:value-of select="section1/value" />
							<xsl:text> </xsl:text>
							Section
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="score1/value">
						<strong>Score:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="score1/value" />
						<br />
					</xsl:if>
					<xsl:if test="percent1/value">
						<strong>Percentile:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="percent1/value" />
						<br />
					</xsl:if>

					<xsl:if test="(section2/value) or (score2/value) or (percent2/value)">
						<br />
					</xsl:if>
					<xsl:if test="section2/value">
						<strong>
							<xsl:value-of select="section2/value" />
							<xsl:text> </xsl:text>
							Section
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="score2/value">
						<strong>Score:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="score2/value" />
						<br />
					</xsl:if>
					<xsl:if test="percent2/value">
						<strong>Percentile:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="percent2/value" />
						<br />
					</xsl:if>

					<xsl:if test="(section3/value) or (score3/value) or (percent3/value)">
						<br />
					</xsl:if>
					<xsl:if test="section3/value">
						<strong>
							<xsl:value-of select="section3/value" />
							<xsl:text> </xsl:text>
							Section
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="score3/value">
						<strong>Score:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="score3/value" />
						<br />
					</xsl:if>
					<xsl:if test="percent3/value">
						<strong>Percentile:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="percent3/value" />
						<br />
					</xsl:if>

					<xsl:if test="(section4/value) or (score4/value) or (percent4/value)">
						<br />
					</xsl:if>
					<xsl:if test="section4/value">
						<strong>
							<xsl:value-of select="section4/value" />
							<xsl:text> </xsl:text>
							Section
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="score4/value">
						<strong>Score:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="score4/value" />
						<br />
					</xsl:if>
					<xsl:if test="percent4/value">
						<strong>Percentile:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="percent4/value" />
						<br />
					</xsl:if>

					<xsl:if test="(section5/value) or (score5/value) or (percent5/value)">
						<br />
					</xsl:if>
					<xsl:if test="section5/value">
						<strong>
							<xsl:value-of select="section5/value" />
							<xsl:text> </xsl:text>
							Section
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="score5/value">
						<strong>Score:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="score5/value" />
						<br />
					</xsl:if>
					<xsl:if test="percent5/value">
						<strong>Percentile:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="percent5/value" />
						<br />
					</xsl:if>

					<xsl:if test="(section6/value) or (score6/value) or (percent6/value)">
						<br />
					</xsl:if>
					<xsl:if test="section6/value">
						<strong>
							<xsl:value-of select="section6/value" />
							<xsl:text> </xsl:text>
							Section
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="score6/value">
						<strong>Score:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="score6/value" />
						<br />
					</xsl:if>
					<xsl:if test="percent6/value">
						<strong>Percentile:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="percent6/value" />
						<br />
					</xsl:if>

					<xsl:if test="composite/value">
						<br />
						<strong>Composite Value:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="composite/value" />
						<br />
					</xsl:if>
					<xsl:if test="total/value">
						<br />
						<strong>Total:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="total/value" />
						<br />
					</xsl:if>

					<xsl:if test="interpretation/value">
						<br />
						<strong>Interpretation:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="interpretation/value" />
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
