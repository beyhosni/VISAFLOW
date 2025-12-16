"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import api from "@/lib/api";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Loader2, Upload, File, CheckCircle } from "lucide-react";
import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";

export default function DocumentsPage({ params }: { params: { id: string } }) {
    const queryClient = useQueryClient();
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [selectedType, setSelectedType] = useState<string>("PASSPORT"); // Default for demo

    const { data: documents, isLoading } = useQuery({
        queryKey: ["documents", params.id],
        queryFn: async () => {
            const res = await api.get(`/cases/${params.id}/documents`);
            return res.data;
        }
    });

    const mutation = useMutation({
        mutationFn: async () => {
            if (!selectedFile) return;
            const formData = new FormData();
            formData.append("file", selectedFile);
            formData.append("type", selectedType);

            return api.post(`/cases/${params.id}/documents`, formData, {
                headers: { "Content-Type": "multipart/form-data" }
            });
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["documents", params.id] });
            setSelectedFile(null);
            alert("Document uploaded!");
        }
    });

    // Mock Requirements List
    const requiredDocs = [
        { type: "PASSPORT", label: "Petitioner Passport" },
        { type: "MARRIAGE_CERTIFICATE", label: "Marriage Certificate" },
        { type: "NATURALIZATION_CERTIFICATE", label: "Naturalization Certificate" }
    ];

    if (isLoading) return <Loader2 className="animate-spin" />;

    return (
        <div className="space-y-6">
            <div>
                <h3 className="text-lg font-medium">Documents</h3>
                <p className="text-sm text-muted-foreground">Upload required evidence.</p>
            </div>

            <div className="grid gap-6 md:grid-cols-2">
                <Card>
                    <CardHeader>
                        <CardTitle className="text-sm font-medium">Upload New Document</CardTitle>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        <select
                            className="flex h-10 w-full items-center justify-between rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                            value={selectedType}
                            onChange={(e) => setSelectedType(e.target.value)}
                        >
                            {requiredDocs.map(d => (
                                <option key={d.type} value={d.type}>{d.label}</option>
                            ))}
                        </select>

                        <Input
                            type="file"
                            onChange={(e) => setSelectedFile(e.target.files?.[0] || null)}
                        />

                        <Button
                            className="w-full"
                            disabled={!selectedFile || mutation.isPending}
                            onClick={() => mutation.mutate()}
                        >
                            {mutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                            <Upload className="mr-2 h-4 w-4" /> Upload
                        </Button>
                    </CardContent>
                </Card>

                <Card>
                    <CardHeader>
                        <CardTitle className="text-sm font-medium">Uploaded Documents</CardTitle>
                    </CardHeader>
                    <CardContent>
                        {documents?.length === 0 ? (
                            <p className="text-sm text-muted-foreground">No documents uploaded yet.</p>
                        ) : (
                            <ul className="space-y-2">
                                {documents?.map((doc: any) => (
                                    <li key={doc.id} className="flex items-center justify-between p-2 border rounded-md">
                                        <div className="flex items-center space-x-2">
                                            <File className="h-4 w-4 text-blue-500" />
                                            <span className="text-sm font-medium">{doc.documentType}</span>
                                        </div>
                                        <Badge variant="secondary" className="text-xs">
                                            <CheckCircle className="h-3 w-3 mr-1" /> Uploaded
                                        </Badge>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
