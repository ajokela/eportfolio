<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="AdditionalPhoto">
		<xsl:param name="entryName">
			Additional Photo
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
				</td>
				<td class="elemPresentationCell2">

					<xsl:value-of select="entryName/value" />
					<br />
					<xsl:value-of select="dateTaken/value" />
					<br />
					<xsl:value-of disable-output-escaping="yes"
						select="additionalInfo/value" />
					<br />

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

	<xsl:template
		match="AdditionalPhoto-list|AdditionalPhoto_1-list|AdditionalPhoto_2-list|AdditionalPhoto_3-list|AdditionalPhoto_4-list|AdditionalPhoto_5-list|AdditionalPhoto_6-list|AdditionalPhoto_7-list|AdditionalPhoto_8-list|AdditionalPhoto_9-list|AdditionalPhoto_10-list|AdditionalPhoto_11-list|AdditionalPhoto_12-list|AdditionalPhoto_13-list|AdditionalPhoto_14-list|AdditionalPhoto_15-list|AdditionalPhoto_16-list|AdditionalPhoto_17-list|AdditionalPhoto_18-list|AdditionalPhoto_19-list|AdditionalPhoto_20-list|AdditionalPhoto_21-list|AdditionalPhoto_22-list|AdditionalPhoto_23-list|AdditionalPhoto_24-list|AdditionalPhoto_25-list|AdditionalPhoto_26-list|AdditionalPhoto_27-list|AdditionalPhoto_28-list|AdditionalPhoto_29-list|AdditionalPhoto_30-list|AdditionalPhoto_31-list|AdditionalPhoto_32-list|AdditionalPhoto_33-list|AdditionalPhoto_34-list|AdditionalPhoto_35-list|AdditionalPhoto_36-list|AdditionalPhoto_37-list|AdditionalPhoto_38-list|AdditionalPhoto_39-list|AdditionalPhoto_40-list|AdditionalPhoto_41-list|AdditionalPhoto_42-list|AdditionalPhoto_43-list|AdditionalPhoto_44-list|AdditionalPhoto_45-list|AdditionalPhoto_46-list|AdditionalPhoto_47-list|AdditionalPhoto_48-list|AdditionalPhoto_49-list|AdditionalPhoto_50-list|AdditionalPhoto_51-list|AdditionalPhoto_52-list|AdditionalPhoto_53-list|AdditionalPhoto_54-list|AdditionalPhoto_55-list|AdditionalPhoto_56-list|AdditionalPhoto_57-list|AdditionalPhoto_58-list|AdditionalPhoto_59-list|AdditionalPhoto_60-list|AdditionalPhoto_61-list|AdditionalPhoto_62-list|AdditionalPhoto_63-list|AdditionalPhoto_64-list|AdditionalPhoto_65-list|AdditionalPhoto_66-list|AdditionalPhoto_67-list|AdditionalPhoto_68-list|AdditionalPhoto_69-list|AdditionalPhoto_70-list|AdditionalPhoto_71-list|AdditionalPhoto_72-list|AdditionalPhoto_73-list|AdditionalPhoto_74-list|AdditionalPhoto_75-list|AdditionalPhoto_76-list|AdditionalPhoto_77-list|AdditionalPhoto_78-list|AdditionalPhoto_79-list|AdditionalPhoto_80-list|AdditionalPhoto_81-list|AdditionalPhoto_82-list|AdditionalPhoto_83-list|AdditionalPhoto_84-list|AdditionalPhoto_85-list|AdditionalPhoto_86-list|AdditionalPhoto_87-list|AdditionalPhoto_88-list|AdditionalPhoto_89-list|AdditionalPhoto_90-list|AdditionalPhoto_91-list|AdditionalPhoto_92-list|AdditionalPhoto_93-list|AdditionalPhoto_94-list|AdditionalPhoto_95-list|AdditionalPhoto_96-list|AdditionalPhoto_97-list|AdditionalPhoto_98-list|AdditionalPhoto_99-list|AdditionalPhoto_100-list">
		<xsl:param name="entryName">
			Additional Photo
		</xsl:param>
		<xsl:for-each select="AdditionalPhoto">
			<xsl:apply-templates select=".">
				<xsl:with-param name="entryName">
					<xsl:value-of select="$entryName" />
				</xsl:with-param>
			</xsl:apply-templates>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
