"use client";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import api from "@/lib/api";
import { useMutation } from "@tanstack/react-query";
import { Loader2, Download } from "lucide-react";

export default function PackagePage({ params }: { params: { id: string } }) {

    // Stub data for MVP
    const mutation = useMutation({
        mutationFn: async () => {
            // In real app, trigger generation, polling status, etc.
            // Here we just call the form download endpoint with some data
            // Or fetching the final file if generated
            // For MVP, just hitting the generate endpoint directly to download

            // Since FormController doesn't take caseId but raw data in MVP, 
            // we'll assume there is an endpoint like GET /api/cases/{id}/package
            // If not, we simulated it in Workflow. 
            // Let's stub "Generation" as success

            return new Promise((resolve) => setTimeout(resolve, 2000));
        }
    });

    const handleDownload = async () => {
        try {
            // Using the FormController stub for demo
            const res = await api.post("/forms/generate", {
                "Petitioner": "John Doe",
                "Beneficiary": "Jane Smith"
            }, { responseType: 'blob' });

            const url = window.URL.createObjectURL(new Blob([res.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'application.pdf');
            document.body.appendChild(link);
            link.click();
        } catch (e) {
            alert("Failed to download");
        }
    };

    return (
        <div className="space-y-6">
            <div>
                <h3 className="text-lg font-medium">Final Package</h3>
                <p className="text-sm text-muted-foreground">Download and print your complete application.</p>
            </div>

            <Card>
                <CardHeader>
                    <CardTitle className="text-sm font-medium">Download Package</CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                    <p className="text-sm text-muted-foreground">
                        Your application package includes Form I-130, I-130A, and the Cover Letter.
                    </p>

                    <div className="flex gap-4">
                        <Button onClick={handleDownload}>
                            <Download className="mr-2 h-4 w-4" /> Download PDF
                        </Button>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
}
