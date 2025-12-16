"use client";

import { QuestionnaireForm } from "@/components/questionnaire-form";
import api from "@/lib/api";
import { useQuery } from "@tanstack/react-query";
import { Loader2 } from "lucide-react";

export default function QuestionnairePage({ params }: { params: { id: string } }) {
    const { data: questions, isLoading: qLoading } = useQuery({
        queryKey: ["questions", params.id],
        queryFn: async () => {
            const res = await api.get(`/cases/${params.id}/questionnaire/questions?type=I-130-PETITIONER`); // Simplified type selection
            return res.data;
        }
    });

    const { data: answers, isLoading: aLoading } = useQuery({
        queryKey: ["answers", params.id],
        queryFn: async () => {
            const res = await api.get(`/cases/${params.id}/questionnaire/answers`);
            return res.data;
        }
    });

    if (qLoading || aLoading) return <Loader2 className="animate-spin" />;

    return (
        <div className="max-w-3xl space-y-6">
            <div>
                <h3 className="text-lg font-medium">Questionnaire</h3>
                <p className="text-sm text-muted-foreground">Please answer all questions accurately.</p>
            </div>

            <QuestionnaireForm
                caseId={params.id}
                questions={questions || []}
                initialAnswers={answers || {}}
            />
        </div>
    );
}
