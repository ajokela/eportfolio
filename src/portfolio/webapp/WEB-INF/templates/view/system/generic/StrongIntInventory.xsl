<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="StrongIntInventory">
		<xsl:param name="entryName">
			Strong Interest Inventory
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<xsl:if test="inventoryDate/value">
						<strong>Date Taken: </strong>
						<xsl:value-of select="inventoryDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="realistic/value">
						<br />
						<i>
							Key:
							<br />
							V = Very high
							<br />
							H = High
							<br />
							A = Average
							<br />
							L = Little
							<br />
							VL = Very little
						</i>
						<br />
						<br />
						<strong>Realistic: </strong>
						<xsl:value-of select="realistic/value" />
						<br />
					</xsl:if>
					<xsl:if test="investigative/value">
						<strong>Investigative: </strong>
						<xsl:value-of select="investigative/value" />
						<br />
					</xsl:if>
					<xsl:if test="artistic/value">
						<strong>Artistic: </strong>
						<xsl:value-of select="artistic/value" />
						<br />
					</xsl:if>
					<xsl:if test="social/value">
						<strong>Social: </strong>
						<xsl:value-of select="social/value" />
						<br />
					</xsl:if>
					<xsl:if test="enterprising/value">
						<strong>Enterprising: </strong>
						<xsl:value-of select="enterprising/value" />
						<br />
					</xsl:if>
					<xsl:if test="conventional/value">
						<strong>Conventional: </strong>
						<xsl:value-of select="conventional/value" />
						<br />
					</xsl:if>
					<xsl:if test="interest1/value">
						<br />
						<strong>Basic Interest Scales: </strong>
						<br />
						1.
						<xsl:value-of select="interest1/value" />
						<br />
					</xsl:if>
					<xsl:if test="interest2/value">
						2.
						<xsl:value-of select="interest2/value" />
						<br />
					</xsl:if>
					<xsl:if test="interest3/value">
						3.
						<xsl:value-of select="interest3/value" />
						<br />
					</xsl:if>
					<xsl:if test="interest4/value">
						4.
						<xsl:value-of select="interest4/value" />
						<br />
					</xsl:if>
					<xsl:if test="interest5/value">
						5.
						<xsl:value-of select="interest5/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation1/value">
						<br />
						<strong>Occupational Scales:</strong>
						<br />
						1.
						<xsl:value-of select="occupation1/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation2/value">
						2.
						<xsl:value-of select="occupation2/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation3/value">
						3.
						<xsl:value-of select="occupation3/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation4/value">
						4.
						<xsl:value-of select="occupation4/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation5/value">
						5.
						<xsl:value-of select="occupation5/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation6/value">
						6.
						<xsl:value-of select="occupation6/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation7/value">
						7.
						<xsl:value-of select="occupation7/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation8/value">
						8.
						<xsl:value-of select="occupation8/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation9/value">
						9.
						<xsl:value-of select="occupation9/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation10/value">
						10.
						<xsl:value-of select="occupation10/value" />
						<br />
					</xsl:if>
					<xsl:if test="interpretation/value">
						<br />
						<strong>Interpretation/Reaction: </strong>
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
