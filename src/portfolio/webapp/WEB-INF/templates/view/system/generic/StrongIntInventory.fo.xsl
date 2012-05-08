<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="StrongIntInventory">
		<xsl:param name="entryName">
			Strong Interest Inventory
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
							<!--xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Inventory: </fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="inventoryDate/value">
								<fo:inline font-weight="bold">Date Taken:</fo:inline>
								<xsl:value-of select="inventoryDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="realistic/value">
								<fo:block></fo:block>
								Key:
								<fo:block></fo:block>
								V = Very high
								<fo:block></fo:block>
								H = High
								<fo:block></fo:block>
								A = Average
								<fo:block></fo:block>
								L = Little
								<fo:block></fo:block>
								VL = Very little
								<fo:block></fo:block>
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Realistic:</fo:inline>
								<xsl:value-of select="realistic/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="investigative/value">
								<fo:inline font-weight="bold">Investigative:</fo:inline>
								<xsl:value-of select="investigative/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="artistic/value">
								<fo:inline font-weight="bold">Artistic:</fo:inline>
								<xsl:value-of select="artistic/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="social/value">
								<fo:inline font-weight="bold">Social:</fo:inline>
								<xsl:value-of select="social/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="enterprising/value">
								<fo:inline font-weight="bold">Enterprising:</fo:inline>
								<xsl:value-of select="enterprising/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="conventional/value">
								<fo:inline font-weight="bold">Conventional:</fo:inline>
								<xsl:value-of select="conventional/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interest1/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Basic Interest Scales:</fo:inline>
								<fo:block></fo:block>
								1.
								<xsl:value-of select="interest1/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interest2/value">
								2.
								<xsl:value-of select="interest2/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interest3/value">
								3.
								<xsl:value-of select="interest3/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interest4/value">
								4.
								<xsl:value-of select="interest4/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interest5/value">
								5.
								<xsl:value-of select="interest5/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation1/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Occupational Scales:</fo:inline>
								<fo:block></fo:block>
								1.
								<xsl:value-of select="occupation1/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation2/value">
								2.
								<xsl:value-of select="occupation2/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation3/value">
								3.
								<xsl:value-of select="occupation3/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation4/value">
								4.
								<xsl:value-of select="occupation4/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation5/value">
								5.
								<xsl:value-of select="occupation5/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation6/value">
								6.
								<xsl:value-of select="occupation6/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation7/value">
								7.
								<xsl:value-of select="occupation7/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation8/value">
								8.
								<xsl:value-of select="occupation8/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation9/value">
								9.
								<xsl:value-of select="occupation9/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="occupation10/value">
								10.
								<xsl:value-of select="occupation10/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interpretation/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Interpretation/Reaction:</fo:inline>
								<fo:block></fo:block>
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
